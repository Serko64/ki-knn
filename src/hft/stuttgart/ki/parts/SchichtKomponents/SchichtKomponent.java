package hft.stuttgart.ki.parts.SchichtKomponents;

import hft.stuttgart.ki.parts.top.SchichtBuilder;

public class SchichtKomponent implements SchichtBuilder{
	private long id;
	private SchichtTyp type;
	
	@Override
	public long generateComponentId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> boolean createType() {
		// TODO Auto-generated method stub
		return false;
	}

}
