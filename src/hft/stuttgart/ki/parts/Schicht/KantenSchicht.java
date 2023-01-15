package hft.stuttgart.ki.parts.Schicht;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtTyp;

public class KantenSchicht {
private UUID schichtIDF;
private UUID schichtIDB;
private ArrayList<KanteTyp> kanten;
public KantenSchicht(UUID schichtIDF, UUID schichtIDB, ArrayList<SchichtKomponent> frontKnoten, ArrayList<SchichtKomponent> backKnoten) {
	this.schichtIDB = schichtIDB;
	this.schichtIDF = schichtIDF;
	this.kanten = new ArrayList<>();
	generateKanten(frontKnoten, backKnoten);
}
public boolean generateRandomGewichte() {
	for (KanteTyp kanteTyp : kanten) {
		double rand = Math.random();
		if(rand > 0.5) {
			kanteTyp.setGewicht(-Math.random());
		} else {
			kanteTyp.setGewicht(Math.random());
		}
	}
	return true;
}
//front von links
public void generateKanten(ArrayList<SchichtKomponent> fK, ArrayList<SchichtKomponent> bK) {
	for (SchichtKomponent kT : fK) {
		for(int i = 0;i<bK.size();i++) {
			if(!((KnotenTyp)bK.get(i).getType()).isBias()) {
				kanten.add(new KanteTyp(kT.getId() , bK.get(i).getId()));
			}
		}
	}
	generateRandomGewichte();
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
