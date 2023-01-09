package hft.stuttgart.ki.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

record werte(Double x1, Double x2, Double y) {
};

public class Daten {

	public ArrayList<werte> datenliste;

	public Daten() throws IOException {
		datenliste = new ArrayList<>();
		readFile();
	}

	public void readFile() throws IOException {

		try {
			BufferedReader reader = new BufferedReader(new FileReader("Wetter.txt"));
			String zeile;
			while ((zeile = reader.readLine()) != null) {
				datenliste.add(new werte(Double.valueOf(zeile.split("\t")[0]), Double.valueOf(zeile.split("\t")[1]),
						Double.valueOf(zeile.split("\t")[2])));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Couldnt read the file");
		}
	}

	public ArrayList<werte> getDatenliste() {
		return datenliste;
	}
}
