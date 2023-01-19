package hft.stuttgart.ki.main;

import java.io.IOException;
import java.util.ArrayList;
import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.parts.Schicht.Ausgabe;
import hft.stuttgart.ki.parts.Schicht.Eingabe;
import hft.stuttgart.ki.parts.Schicht.Hidden;
import hft.stuttgart.ki.parts.Schicht.KantenSchicht;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.EingabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.HiddenKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.top.EFC;
import hft.stuttgart.ki.vis.showNN;

public class Netz {
private ArrayList<Object> netz;
private double alpha = 0.4;
private Daten daten;


public Daten getDaten() {
	return daten;
}
public Netz() throws IOException {
	super();
	this.daten = new Daten();
}
public ArrayList<Integer> doEpoche(showNN window) throws IOException, InterruptedException {
	ArrayList<Integer> collectedData = new ArrayList<>();
	ArrayList<Double> valData = new ArrayList<>();
	ArrayList<Integer> collectedDataR = new ArrayList<>();
	for(int i = 0; i<daten.getSAVEDDatenliste().size();i++) {
		if(!selectRanData()) {
			resetTemp();
		}
		doForwardCalculation();
		AusgabeKnoten rk = ((AusgabeKnoten)((Schicht)netz.get(netz.size()-1)).getType().getKnoten().get(0).getType());
		if(rk.getOxVal() >= 0.5 && rk.getYk() == 1) {
			collectedData.add(1);
			valData.add(rk.getOxVal());
			collectedDataR.add((int) rk.getYk());
		} else if(rk.getOxVal() < 0.5 && rk.getYk() == 0){
			collectedData.add(1);
			valData.add(rk.getOxVal());
			collectedDataR.add((int) rk.getYk());
		} else {
			collectedData.add(0);
			valData.add(rk.getOxVal());
			collectedDataR.add((int) rk.getYk());
		}
		doBackwardCalculation();
		doGewichteAktualisieren();
	}
	window.getDrawer().setRight(collectedData);
	window.getDrawer().setValSaver(valData);
	window.getDrawer().setResultSaver(collectedDataR);
	window.getDrawer().repaint();
	return collectedData;
}
public void resetTemp() {
	daten.resetTemp();
}
public boolean selectRanData() throws IOException {
	int val = 0;
	if(daten.getDatenliste().size() == 0) {
		return false;
	}
	if(daten.getDatenliste().size() == 1) {
		val = 0;
	} else {
		do {
			val = (int) (Math.random()*10);
		} while(val > daten.getDatenliste().size()-1);
	}
	Werte werte = daten.getDatenliste().get(val);
	daten.getDatenliste().remove(val);
	ArrayList<SchichtKomponent> knoten1 = ((Schicht)netz.get(0)).getType().getKnoten();
	((KnotenTyp)knoten1.get(1).getType()).setOxVal(werte.x1());
	((KnotenTyp)knoten1.get(2).getType()).setOxVal(werte.x2());
	((KnotenTyp)knoten1.get(0).getType()).setOxVal(1);
	((AusgabeKnoten)((Schicht)netz.get(netz.size()-1)).getType().getKnoten().get(0).getType()).setYk(werte.y());;
	return true;
}
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
		for(int i = 0; i<netz.size()-1;i++) {
			mitKantenNetz.add(new KantenSchicht(((Schicht) netz.get(i)).getId(), ((Schicht) netz.get(i+1)).getId(), ((Schicht) netz.get(i)).getType().getKnoten(), ((Schicht) netz.get(i+1)).getType().getKnoten() ));
		}
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
	}
public void doForwardCalculation() {
		for(int i = 2;i<netz.size();i = i+2) {
			for (SchichtKomponent s: ((Schicht)netz.get(i)).getType().getKnoten()) {
				if(((KnotenTyp)s.getType()).isBias()) {
					continue;
				} else {
				ArrayList<KanteTyp> zBK = ((KantenSchicht)netz.get(i-1)).findConnectedBackKnotenId(s.getId());
				int[] ui = new int[]{i};
				
				((KnotenTyp) s.getType()).setIxVal(zBK.stream().mapToDouble(n -> {
					return n.getGewicht() * ((KnotenTyp)((Schicht)netz.get(ui[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal();}).sum());
				((KnotenTyp) s.getType()).setOxVal(new EFC().sig(((KnotenTyp) s.getType()).getIxVal()));
				}
			}
		}
	}
public void doBackwardCalculation() {
		double deltaJ = 0;
		for(int i = netz.size()-1;i>2;i = i-2) {
			if(i == netz.size()-1 && ((Schicht)netz.get(i)).getType().getClass().equals(Ausgabe.class)) {
				int[] counter = new int[] {0};
				((Schicht)netz.get(i)).getType().getKnoten().stream().mapToDouble(n -> new EFC().sigA(((AusgabeKnoten)n.getType()).getIxVal()) * (((AusgabeKnoten)n.getType()).getYk() - ((AusgabeKnoten)n.getType()).getOxVal())).forEach(m -> {
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
				if(((Schicht)netz.get(i)).getType().getClass().equals(Ausgabe.class)) {
					deltaJ = zBK.stream().mapToDouble(n -> n.getGewicht() * ((AusgabeKnoten)((Schicht)netz.get(ui[0])).getType().findAllKnoten(n.getKnotenIDB()).get(0).getType()).getDeltaK()).sum();
					((HiddenKnoten) s.getType()).setDeltaJ(new EFC().sigA(((HiddenKnoten) s.getType()).getIxVal()) * deltaJ);
				}
				else if(((Schicht)netz.get(i)).getType().getClass().equals(Hidden.class)) {
					deltaJ = zBK.stream().mapToDouble(n -> n.getGewicht() * ((HiddenKnoten)((Schicht)netz.get(ui[0])).getType().findAllKnoten(n.getKnotenIDB()).get(0).getType()).getDeltaJ()).sum();
					((HiddenKnoten) s.getType()).setDeltaJ(new EFC().sigA(((HiddenKnoten) s.getType()).getIxVal()) * deltaJ);
				}
			}
		}
	}
public void doGewichteAktualisieren() {
		for(int i = netz.size()-1;i>0;i = i-2) {
			for (SchichtKomponent s: ((Schicht)netz.get(i)).getType().getKnoten()) {
				ArrayList<KanteTyp> zBK = ((KantenSchicht)netz.get(i-1)).findConnectedBackKnotenId(s.getId());
				int[] counter = new int[] {0};
				int[] iu = new int[] {i};
				if(((Schicht)netz.get(i)).getType().getClass().equals(Ausgabe.class)) {
				zBK.stream().mapToDouble(n -> n.getGewicht() + alpha * ((HiddenKnoten)((Schicht)netz.get(iu[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal() * ((AusgabeKnoten)s.getType()).getDeltaK()).forEach(m -> {
					((KanteTyp)((KantenSchicht)netz.get(iu[0]-1)).getKanten().get(counter[0])).setGewicht(m);
					counter[0]++;
				});;
				} 
				else if(((Schicht)netz.get(i)).getType().getClass().equals(Hidden.class) && ((Schicht)netz.get(i-2)).getType().getClass().equals(Hidden.class)) {
					zBK.stream().mapToDouble(n -> n.getGewicht() + alpha * ((HiddenKnoten)((Schicht)netz.get(iu[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal() * ((HiddenKnoten)s.getType()).getDeltaJ()).forEach(m -> {
						((KanteTyp)((KantenSchicht)netz.get(iu[0]-1)).getKanten().get(counter[0])).setGewicht(m);
						counter[0]++;
					});;
				} else {
					zBK.stream().mapToDouble(n -> n.getGewicht() + alpha * ((EingabeKnoten)((Schicht)netz.get(iu[0]-2)).getType().findAllKnoten(n.getKnotenIDF()).get(0).getType()).getOxVal() * ((HiddenKnoten)s.getType()).getDeltaJ()).forEach(m -> {
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
public double getAlpha() {
	return alpha;
}

}
