package hft.stuttgart.ki.parts.Schicht;
import java.util.ArrayList;
import hft.stuttgart.ki.parts.SchichtKomponents.Knoten;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtTyp;

public class BereichsTyp {
private ArrayList<SchichtKomponent> knoten;


public BereichsTyp() {
	this.knoten = new ArrayList<>();
}

public ArrayList<SchichtKomponent> getKnoten() {
	return knoten;
}

}
