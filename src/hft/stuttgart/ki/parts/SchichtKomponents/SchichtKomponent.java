package hft.stuttgart.ki.parts.SchichtKomponents;

import java.util.UUID;

import hft.stuttgart.ki.parts.top.SchichtBuilder;

public class SchichtKomponent implements SchichtBuilder{
	private UUID id;
	private SchichtTyp type;
	
	public SchichtKomponent(SchichtTyp type) {
		this.id = generateComponentId();
		createType(type);
	}

	@Override
	public UUID generateComponentId() {
		return UUID.randomUUID();
	}

	public boolean createType(SchichtTyp t) {
		if(t.getClass().getSuperclass().equals(KnotenTyp.class) || t.getClass().equals(KanteTyp.class)) {
			this.type = t;
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

	public void setId(UUID id) {
		this.id = id;
	}
	
}
