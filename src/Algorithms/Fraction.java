package Algorithms;
import java.util.*;
import java.io.*;

public class Fraction {
	int numer;
	int denom;
	public Fraction(int n, int d) {
		numer = n;
		denom = d;
	}
	public Fraction add(Fraction b) {
		int n = numer * b.denom + b.numer * denom;
		int d = denom * b.denom;
		return (new Fraction(n, d)).simplify();
	}
	public Fraction multiply(Fraction b) {
		int n = numer * b.numer;
		int d = denom * b.denom;
		return new Fraction(n,d).simplify();
	}
	public Fraction getReciprocal() {
		return new Fraction(denom, numer);
	}
	public Fraction simplify() {
		int x = GCD(numer, denom);
		numer = numer / x;
		denom = denom / x;
		return this;
	}
	public Fraction divide(Fraction b) {
		return multiply(b.getReciprocal());
	}
	private static int GCD(int a, int b) {
		if(b == 0)
			return a;
		return GCD(b, a % b);
	}
}
