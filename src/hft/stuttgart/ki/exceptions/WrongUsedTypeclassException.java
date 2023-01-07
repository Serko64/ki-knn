package hft.stuttgart.ki.exceptions;

public class WrongUsedTypeclassException extends Exception{
	public WrongUsedTypeclassException() {
		super("Used wrong class for Schicht or Kante");
	}
}
