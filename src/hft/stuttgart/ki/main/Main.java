package hft.stuttgart.ki.main;

import java.awt.EventQueue;
import java.util.ArrayList;

import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.vis.showNN;

public class Main {

	public static void main(String[] args) throws WrongUsedTypeclassException {
		Netz netz = new Netz();
		int eZ = 3;
		int[] hZ = new int[] {4,4};
		int aZ = 1;
		netz.createKnoten(eZ,hZ,aZ);
		netz.addKanten();
		ArrayList<SchichtKomponent> knoten1 = ((Schicht)netz.getNetz().get(0)).getType().getKnoten();
		((KnotenTyp)knoten1.get(0).getType()).setOxVal(1);
		((KnotenTyp)knoten1.get(1).getType()).setOxVal(0.1);
		((KnotenTyp)knoten1.get(2).getType()).setOxVal(0.3);
		((AusgabeKnoten)((Schicht)netz.getNetz().get(6)).getType().getKnoten().get(0).getType()).setYk(1);;
		ArrayList<SchichtKomponent> knoten2 = ((Schicht)netz.getNetz().get(2)).getType().getKnoten();
		System.out.println("Vlll" + ((KnotenTyp)knoten2.get(1).getType()).getOxVal());
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
