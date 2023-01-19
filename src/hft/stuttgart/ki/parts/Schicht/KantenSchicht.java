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
			kanteTyp.setGewicht(-Math.random()*5);
		} else {
			kanteTyp.setGewicht(Math.random()*5);
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
@Override
public String toString() {
	ArrayList<String> resultKanten = new ArrayList<>();
	UUID lastF = null;
	int kantenCounterF = 0;
	int kantenCounterB = 0;
	for (int i = 0;i<kanten.size();i++) {
		if(lastF != null && lastF.equals(kanten.get(i).getKnotenIDF())) {
			resultKanten.add(kantenCounterF +". Knoten-Links , Gewicht: "+kanten.get(i).getGewicht()+ " , " + ++kantenCounterB+ ". Knoten-Rechts");
		} else {
			kantenCounterB = 1;
			lastF = kanten.get(i).getKnotenIDF();
			resultKanten.add(++kantenCounterF +". Knoten-Links , Gewicht: "+kanten.get(i).getGewicht()+ " , " + kantenCounterB + ". Knoten-Rechts");
		}
	}
	for (String string : resultKanten) {
		System.out.println(string);
	}
	return resultKanten.toString();
}

}
