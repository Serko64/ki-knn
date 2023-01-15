package hft.stuttgart.ki.main;

import java.util.ArrayList;
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
	netz = new ArrayList<>();
		netz.add(new Schicht(new Eingabe(), eingA));
		for (int i : hiddA) {
			netz.add(new Schicht(new Hidden(), i));
		}
		netz.add(new Schicht(new Ausgabe(), ausgA));
	}
public void addKanten() {
	ArrayList<Object> mitKantenNetz = new ArrayList<>();
	System.out.println(netz.size());
		for(int i = 0; i<netz.size()-1;i++) {
			System.out.println("runned");
			mitKantenNetz.add(new KantenSchicht(((Schicht) netz.get(i)).getId(), ((Schicht) netz.get(i+1)).getId(), ((Schicht) netz.get(i)).getType().getKnoten(), ((Schicht) netz.get(i+1)).getType().getKnoten() ));
		}
		System.out.println("d" + mitKantenNetz.size());
	ArrayList<Object> temp = new ArrayList<>();
	temp.addAll(netz);
	netz.clear();
	int counter = 0;
	int counter2 = 0;
		for(int i = 0; i<temp.size()+mitKantenNetz.size();i++) {
			if(i%2 == 0) {
				netz.add(temp.get(counter));
				counter++;
			} else {
				netz.add(mitKantenNetz.get(counter2));
				counter2++;
			}
		}
		System.out.println("tets " + netz.size());
	}
public void doForwardCalculation() {
		for(int i = 2;i<netz.size();i = i+2) {
			System.out.println("val "+ (i-1));
			for (SchichtKomponent s: ((Schicht)netz.get(i)).getType().getKnoten()) {
				if(((KnotenTyp)s.getType()).isBias()) {
					continue;
				} else {
				ArrayList<KanteTyp> zBK = ((KantenSchicht)netz.get(i-1)).findConnectedBackKnotenId(s.getId());
				int[] ui = new int[]{i};
				
				((KnotenTyp) s.getType()).setIxVal(zBK.stream().mapToDouble(n -> {
					System.out.println(n.getGewicht() * ((KnotenTyp)((Schicht)netz.get(ui[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal() + " | " + n.getGewicht() + " - "+ ((KnotenTyp)((Schicht)netz.get(ui[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal());
					return n.getGewicht() * ((KnotenTyp)((Schicht)netz.get(ui[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal();}).sum());
				((KnotenTyp) s.getType()).setOxVal(new EFC().sig(((KnotenTyp) s.getType()).getIxVal()));
				zBK.stream().forEach(n -> {
					System.out.println(n.getGewicht());
				});
				System.out.println(((KnotenTyp) s.getType()).getIxVal() + " - " + ((KnotenTyp) s.getType()).getOxVal());
				}
			}
		}
	}
public void doBackwardCalculation() {
	System.out.println(":C");
		double deltaJ = 0;
		for(int i = netz.size()-1;i>2;i = i-2) {
			if(i == netz.size()-1 && netz.get(i).getClass().equals(Ausgabe.class)) {
				int[] counter = new int[] {0};
				((Schicht)netz.get(i)).getType().getKnoten().stream().mapToDouble(n -> new EFC().sigA(((AusgabeKnoten)n.getType()).getIxVal()) * (((AusgabeKnoten)n.getType()).getYk() * ((AusgabeKnoten)n.getType()).getOxVal())).forEach(m -> {
					((AusgabeKnoten)((Schicht)netz.get(netz.size()-1)).getType().getKnoten().get(counter[0]).getType()).setDeltaK(m);
					counter[0]++;
				});;
			}
			for (SchichtKomponent s: ((Schicht)netz.get(i-2)).getType().getKnoten()) {
				if(((HiddenKnoten) s.getType()).isBias()) {
					continue;
				}
				ArrayList<KanteTyp> zBK = ((KantenSchicht)netz.get(i-1)).findConnectedFrontKnotenId(s.getId());
				int[] ui = new int[]{i};
				if(netz.get(i).getClass().equals(Ausgabe.class)) {
					deltaJ = zBK.stream().mapToDouble(n -> n.getGewicht() * ((AusgabeKnoten)((Schicht)netz.get(ui[0])).getType().findAllKnoten(n.getKnotenIDB()).get(0).getType()).getDeltaK()).sum();
					((HiddenKnoten) s.getType()).setDeltaJ(new EFC().sigA(((HiddenKnoten) s.getType()).getIxVal()) * deltaJ);
				}
				else if(netz.get(i).getClass().equals(Hidden.class)) {
					deltaJ = zBK.stream().mapToDouble(n -> n.getGewicht() * ((HiddenKnoten)((Schicht)netz.get(ui[0])).getType().findAllKnoten(n.getKnotenIDB()).get(0).getType()).getDeltaJ()).sum();
					((HiddenKnoten) s.getType()).setDeltaJ(new EFC().sigA(((HiddenKnoten) s.getType()).getIxVal()) * deltaJ);
				}
			}
		}
	}
public void doGewichteAktualisieren() {
		for(int i = netz.size()-1;i>2;i = i-2) {
			for (SchichtKomponent s: ((Schicht)netz.get(i)).getType().getKnoten()) {
				ArrayList<KanteTyp> zBK = ((KantenSchicht)netz.get(i-1)).findConnectedBackKnotenId(s.getId());
				int[] counter = new int[] {0};
				int[] iu = new int[] {i};
				if(netz.get(i).getClass().equals(Ausgabe.class)) {
				zBK.stream().mapToDouble(n -> n.getGewicht() + alpha * ((HiddenKnoten)((Schicht)netz.get(iu[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal() * ((AusgabeKnoten)s.getType()).getDeltaK()).forEach(m -> {
					((KanteTyp)((KantenSchicht)netz.get(iu[0]-1)).getKanten().get(counter[0])).setGewicht(m);
					counter[0]++;
				});;
				} 
				else if(netz.get(i).getClass().equals(Hidden.class)) {
					zBK.stream().mapToDouble(n -> n.getGewicht() + alpha * ((HiddenKnoten)((Schicht)netz.get(iu[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal() * ((HiddenKnoten)s.getType()).getDeltaJ()).forEach(m -> {
						((KanteTyp)((KantenSchicht)netz.get(iu[0]-1)).getKanten().get(counter[0])).setGewicht(m);
						counter[0]++;
					});;
				}
			}
		}
	}

public ArrayList<Object> getNetz() {
	return netz;
}
public void setAlpha(double alpha) {
	this.alpha = alpha;
}
}
