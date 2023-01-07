package hft.stuttgart.ki.parts.Schicht;

import java.util.UUID;

import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.EingabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.HiddenKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtTyp;
import hft.stuttgart.ki.parts.top.SchichtBuilder;

public class Schicht implements SchichtBuilder{
	private UUID id;
	private BereichsTyp type;
	private int amountKnoten;
	
	public Schicht(BereichsTyp type, int amountKnoten) throws WrongUsedTypeclassException {
		super();
		this.id = generateComponentId();
		if(!createType(type)) {
			throw new WrongUsedTypeclassException();
		}
		this.type = type;
	}

	@Override
	public UUID generateComponentId() {
		return UUID.randomUUID();
	}

	public UUID getId() {
		return id;
	}
	
	public boolean createType(BereichsTyp b) {
		if(b.getClass().isInstance(Eingabe.class) || b.getClass().isInstance(Hidden.class) || b.getClass().isInstance(Ausgabe.class)) {
			type = b;
			return true;
		}else{
			return false;
		}
	}

	public BereichsTyp getType() {
		return type;
	}

	public void generateKnoten() {
		for(int i = 0; i<amountKnoten;i++) {
			if(type.getClass().isInstance(Eingabe.class)) {
				type.getKnoten().add(new SchichtKomponent(new EingabeKnoten()));
			}
			else if(type.getClass().isInstance(Hidden.class)) {
				type.getKnoten().add(new SchichtKomponent(new HiddenKnoten()));
			}
			else if(type.getClass().isInstance(Ausgabe.class)) {
				type.getKnoten().add(new SchichtKomponent(new AusgabeKnoten()));
			}
		}
	}
}
