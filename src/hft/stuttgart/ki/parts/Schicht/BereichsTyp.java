package hft.stuttgart.ki.parts.Schicht;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;

public class BereichsTyp {
private ArrayList<SchichtKomponent> knoten;


public BereichsTyp() {
	this.knoten = new ArrayList<>();
}

public ArrayList<SchichtKomponent> getKnoten() {
	return knoten;
}
public ArrayList<SchichtKomponent> findAllKnoten(UUID id) {
	return knoten.stream().filter(m -> m.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
}
}
