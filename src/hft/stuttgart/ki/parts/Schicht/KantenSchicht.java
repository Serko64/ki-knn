package hft.stuttgart.ki.parts.Schicht;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;

public class KantenSchicht {
private UUID schichtIDF;
private UUID schichtIDB;
private ArrayList<KanteTyp> kanten;
public KantenSchicht(UUID schichtIDF, UUID schichtIDB, ArrayList<SchichtKomponent> frontKnoten, ArrayList<SchichtKomponent> backKnoten) {
	this.schichtIDB = schichtIDB;
	this.schichtIDF = schichtIDF;
	generateKanten(frontKnoten, backKnoten);
}
public boolean generateRandomGewichte() {
	for (KanteTyp kanteTyp : kanten) {
		kanteTyp.setGewicht(Math.random());
	}
	return true;
}
public void generateKanten(ArrayList<SchichtKomponent> fK, ArrayList<SchichtKomponent> bK) {
	int counter = 0;
	for (SchichtKomponent kT : fK) {
		kanten.add(new KanteTyp(kT.getId() , bK.get(counter).getId()));
		counter++;
	}
}
public UUID getSchichtIDF() {
	return schichtIDF;
}
public UUID getSchichtIDB() {
	return schichtIDB;
}
public ArrayList<KanteTyp> getKanten() {
	return kanten;
}
public ArrayList<KanteTyp> findConnectedBackKnotenId(UUID id) {
	return kanten.stream().filter(n -> n.getKnotenIDB().equals(id)).collect(Collectors.toCollection(ArrayList::new));
}
public ArrayList<KanteTyp> findConnectedFrontKnotenId(UUID id) {
	return kanten.stream().filter(n -> n.getKnotenIDF().equals(id)).collect(Collectors.toCollection(ArrayList::new));
}
}
