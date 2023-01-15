package hft.stuttgart.ki.parts.top;

public class EFC {
public double sig(double x) {
	return (1/(1+Math.exp(-x)));
}
public double sigA(double x) {
	return sig(x) * (1 - sig(x));
}
}
