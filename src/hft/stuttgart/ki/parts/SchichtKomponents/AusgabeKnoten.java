package hft.stuttgart.ki.parts.SchichtKomponents;

import hft.stuttgart.ki.parts.top.EFC;

public class AusgabeKnoten extends KnotenTyp{
private double deltaK;
private double yk;

public void refreshDeltaK() {
	EFC efc = new EFC();
	deltaK = efc.sigA(super.getIxVal()) * (yk - super.getOxVal());
}

public double getDeltaK() {
	return deltaK;
}

public void setDeltaK(double deltaK) {
	this.deltaK = deltaK;
}

public double getYk() {
	return yk;
}

public void setYk(double yk) {
	this.yk = yk;
}

}
