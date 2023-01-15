package hft.stuttgart.ki.main;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;

import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.vis.showNN;

public class Main {

	public static void main(String[] args) throws WrongUsedTypeclassException, IOException {
		Netz netz = new Netz();
		int eZ = 3;
		int[] hZ = new int[] {3};
		int aZ = 1;
		netz.createKnoten(eZ,hZ,aZ);
		netz.addKanten();
		Daten daten = new Daten();
		Werte werte = daten.getDatenliste().get((int) (Math.random()*10));
		ArrayList<SchichtKomponent> knoten1 = ((Schicht)netz.getNetz().get(0)).getType().getKnoten();
		((KnotenTyp)knoten1.get(1).getType()).setOxVal(werte.x1());
		((KnotenTyp)knoten1.get(2).getType()).setOxVal(werte.x2());
		((KnotenTyp)knoten1.get(0).getType()).setOxVal(1);
		((AusgabeKnoten)((Schicht)netz.getNetz().get(4)).getType().getKnoten().get(0).getType()).setYk(werte.y());;
		ArrayList<SchichtKomponent> knoten2 = ((Schicht)netz.getNetz().get(2)).getType().getKnoten();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					showNN window = new showNN(netz);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
