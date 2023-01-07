package hft.stuttgart.ki.main;

import java.util.ArrayList;
import java.util.UUID;

import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.parts.Schicht.Ausgabe;
import hft.stuttgart.ki.parts.Schicht.Eingabe;
import hft.stuttgart.ki.parts.Schicht.Hidden;
import hft.stuttgart.ki.parts.Schicht.KantenSchicht;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.HiddenKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.top.EFC;

public class Netz {
private ArrayList<Object> netz;
private double alpha;

public void createKnoten(int eingA, int[] hiddA , int ausgA) throws WrongUsedTypeclassException {
	netz.add(new Schicht(new Eingabe(), eingA));
	for (int i : hiddA) {
		netz.add(new Schicht(new Hidden(), hiddA[i]));
	}
	netz.add(new Schicht(new Ausgabe(), ausgA));
}
public void addKanten() {
	for(int i = 1; i<netz.size()-1;i = i+2) {
		netz.add(i,new KantenSchicht(((Schicht) netz.get(i)).getId(), ((Schicht) netz.get(i+1)).getId(), ((Schicht) netz.get(i)).getType().getKnoten(), ((Schicht) netz.get(i)).getType().getKnoten() ));
	}
}
public void doForwardCalculation() {
	double outCal = 0;
	double inCal = 0;
	for(int i = 2;i<netz.size();i = i+2) {
		for (SchichtKomponent s: ((Schicht)netz.get(i)).getType().getKnoten()) {
			for(KanteTyp k : ((KantenSchicht)netz.get(i-1)).getKanten()) {
				if(s.getId().equals(k.getKnotenIDB())) {
					for(SchichtKomponent r: ((Schicht)netz.get(i-2)).getType().getKnoten()) {
						if(r.getId().equals(k.getKnotenIDF())) {
							outCal = outCal + ((KnotenTyp) r.getType()).getOxVal() * k.getGewicht();
							
						}
					}
					}
			}
			inCal = new EFC().sig(outCal);
			((KnotenTyp) s.getType()).setOxVal(outCal);
			((KnotenTyp) s.getType()).setIxVal(inCal);
		}
	}
}
public void doBackwardCalculation() {
	double deltaJ = 0;
	for(int i = netz.size();i>2;i = i-2) {
		for (SchichtKomponent s: ((Schicht)netz.get(i-2)).getType().getKnoten()) {
			for(KanteTyp k : ((KantenSchicht)netz.get(i-1)).getKanten()) {
				if(s.getId().equals(k.getKnotenIDF())) {
					for(SchichtKomponent r: ((Schicht)netz.get(i)).getType().getKnoten()) {
						if(r.getId().equals(k.getKnotenIDB())) {
							if(r.getClass().isInstance(HiddenKnoten.class)) {
								deltaJ = deltaJ + (((HiddenKnoten) r.getType()).getDeltaJ() * k.getGewicht());
							} else if(r.getClass().isInstance(AusgabeKnoten.class)) {
								((AusgabeKnoten) r.getType()).setDeltaK(new EFC().sigA(((AusgabeKnoten) r.getType()).getIxVal()) * (((AusgabeKnoten) r.getType()).getYk() - ((AusgabeKnoten) r.getType()).getOxVal()));
								deltaJ = deltaJ + (((AusgabeKnoten) r.getType()).getDeltaK() * k.getGewicht());
							}
						}
					}
					}
			}
			deltaJ = new EFC().sigA(((KnotenTyp)s.getType()).getIxVal()) * deltaJ;
			((HiddenKnoten) s.getType()).setDeltaJ(deltaJ);
		}
	}
}
public void doGewichteAktualisieren() {
	for(int i = netz.size();i>2;i = i-2) {
		for (SchichtKomponent s: ((Schicht)netz.get(i)).getType().getKnoten()) {
			for(KanteTyp k : ((KantenSchicht)netz.get(i-1)).getKanten()) {
				if(s.getId().equals(k.getKnotenIDF())) {
					for(SchichtKomponent r: ((Schicht)netz.get(i-2)).getType().getKnoten()) {
						if(r.getId().equals(k.getKnotenIDB())) {
							if(r.getClass().isInstance(HiddenKnoten.class)) {
								k.setGewicht(k.getGewicht()+alpha*((KnotenTyp) r.getType()).getOxVal() * ((HiddenKnoten) s.getType()).getDeltaJ());
							}else if(r.getClass().isInstance(AusgabeKnoten.class)) {
								k.setGewicht(k.getGewicht()+alpha*((KnotenTyp) r.getType()).getOxVal() * ((AusgabeKnoten) s.getType()).getDeltaK());
							}
						}
					}
				}
			}
		}
	}
}
}
