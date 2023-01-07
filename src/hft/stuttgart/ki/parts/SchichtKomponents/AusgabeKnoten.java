package hft.stuttgart.ki.parts.SchichtKomponents;

import hft.stuttgart.ki.parts.top.EFC;

public class AusgabeKnoten extends KnotenTyp{
private double deltaK;
private double yk;

public void refreshDeltaK() {
	EFC efc = new EFC();
	deltaK = efc.sigA(super.getIxVal()) * (yk - super.getOxVal());
}
}
