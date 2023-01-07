package hft.stuttgart.ki.main;

import java.util.ArrayList;
import java.util.UUID;

import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.parts.Schicht.Ausgabe;
import hft.stuttgart.ki.parts.Schicht.Eingabe;
import hft.stuttgart.ki.parts.Schicht.Hidden;
import hft.stuttgart.ki.parts.Schicht.KantenSchicht;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.top.EFC;

public class Netz {
private ArrayList<Object> netz;

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
					outCal = outCal + ((KnotenTyp) s.getType()).getOxVal() * k.getGewicht();
				}
			}
		}
		inCal = new EFC().sig(outCal);
	}
}
}
