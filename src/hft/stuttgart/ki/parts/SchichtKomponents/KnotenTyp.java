package hft.stuttgart.ki.parts.SchichtKomponents;

import hft.stuttgart.ki.parts.Schicht.KantenSchicht;
import hft.stuttgart.ki.parts.top.EFC;

public class KnotenTyp extends SchichtTyp{
private boolean bias;
private double oxVal;
private double ixVal;

public void forwardCalIn(KantenSchicht ks) {
	
}
public void forwardCalOut() {
	
}

public double getOxVal() {
	return oxVal;
}
public double getIxVal() {
	return ixVal;
}
public void setOxVal(double oxVal) {
	this.oxVal = oxVal;
}
public void setIxVal(double ixVal) {
	this.ixVal = ixVal;
}
public boolean isBias() {
	return bias;
}
public void setBias(boolean bias) {
	this.bias = bias;
	setOxVal(1);
}

}
