package hft.stuttgart.ki.parts.SchichtKomponents;

import java.util.UUID;

import hft.stuttgart.ki.parts.top.SchichtBuilder;

public class SchichtKomponent implements SchichtBuilder{
	private UUID id;
	private SchichtTyp type;
	
	public SchichtKomponent(SchichtTyp type) {
		createType(type);
	}

	@Override
	public UUID generateComponentId() {
		return UUID.randomUUID();
	}

	public boolean createType(SchichtTyp t) {
		if(t.getClass().isInstance(KnotenTyp.class) || t.getClass().isInstance(KanteTyp.class)) {
			type = t;
			return true;
		}else {
			return false;
		}
	}
	public UUID getId() {
		return id;
	}

	public SchichtTyp getType() {
		return type;
	}
}
