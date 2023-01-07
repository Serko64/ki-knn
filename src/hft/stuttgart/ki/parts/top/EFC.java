package hft.stuttgart.ki.parts.top;

public class EFC {
public double sig(double x) {
	return Math.signum(x);
}
public double sigA(double x) {
	return Math.signum(x) * (1 - Math.signum(x));
}
}
