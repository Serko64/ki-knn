package hft.stuttgart.ki.parts.SchichtKomponents;

import java.util.UUID;

public class KanteTyp extends SchichtTyp{
private double gewicht;
private UUID knotenIDF;
private UUID knotenIDB;

public KanteTyp(UUID uuid, UUID uuid2) {
	this.knotenIDF = uuid;
	this.knotenIDB = uuid2;
}

public void refreshGewicht() {
	
}

public double getGewicht() {
	return gewicht;
}

public void setGewicht(double gewicht) {
	this.gewicht = gewicht;
}

public UUID getKnotenIDF() {
	return knotenIDF;
}

public UUID getKnotenIDB() {
	return knotenIDB;
}

}
