package hft.stuttgart.ki.main;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.vis.showNN;

public class Main {
	private static int counter = 0;
	private static showNN window;
	public static void main(String[] args) throws WrongUsedTypeclassException, IOException, InterruptedException {
		double prozentTrue = 0.0;
		int eZ = 3;
		int aZ = 1;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Anzahl der Hiddenschichten festlegen:");
		int val = scanner.nextInt();
		int[] hZ = new int[val];
		for(int i = 0; i<val;i++) {
			System.out.println((i+1) + ". Hiddenschicht: Anzahl Hiddenknoten + Biasknoten festlegen:");
			hZ[i] = scanner.nextInt();
		}
		Netz netz = new Netz();
		System.out.println("Lernrate Festlegen, Standartmäßig auf: " + netz.getAlpha());
		System.out.println("Neue Lernrate festlegen? y/n");
		if(scanner.next().equals("y")) {
			System.out.println("Neue Lernrate eingeben: (In Double)");
			netz.setAlpha(Double.valueOf(scanner.next()));
		}
		System.out.println("Lege fest ab welcher Anzahl richtiger Vorhersagen die Schleife abbricht (In Prozent ohne das Prozentzeichen):");
		prozentTrue = scanner.nextDouble();
		netz.createKnoten(eZ,hZ,aZ);
		netz.addKanten();
		Daten daten = new Daten();
		window = new showNN(netz);
		window.frame.setVisible(true);
		int counter = 0;
		while(true) {
			window.aktual(counter++);
			ArrayList<Integer> resultvals = netz.doEpoche(window);
			System.out.println("Richtig: " + ((double) resultvals.stream().mapToInt(n -> n).sum() / resultvals.size())*100 + "%");
			if(((((double)resultvals.stream().mapToInt(n -> n).sum() / (double)resultvals.size())*100) >= prozentTrue)) {
				break;
			}
			System.out.println("Epoche: " + counter);
		}
		int hiddCounter = 0;
		for(int i=0;i<netz.getNetz().size();i++) {
			if(i == 0) {
				System.out.println("Gewichte der Eingabeschicht - " + ++hiddCounter+". Hiddenschicht");
			} else if(i == netz.getNetz().size()-3) {
				System.out.println("Gewichte der Hiddenschicht - Ausgabeschicht");
			} else if(i%2 == 0 && i != netz.getNetz().size()-1){
				System.out.println("Gewichte der" + ++hiddCounter+  ". Hiddenschicht - " +  ++hiddCounter+". Hiddenschicht");
			}
			if(i%2 != 0) {
				netz.getNetz().get(i).toString();
			}
		}
	}
}
