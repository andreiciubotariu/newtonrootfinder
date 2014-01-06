package com.andreiciubotariu.newtonrootfinder;

/**
 * @author Andrei Ciubotariu
 */
public class X extends Function {

	public X() {
	}

	@Override
	public double computeFor(double value) {
		return value;
	}

	@Override
	public Function computeFirstDerivative() {
		return new Const(1);
	}

	@Override
	public String displayString() {
		return toString();
	}

	@Override
	public Function sameType() {
		return new X();
	}

	@Override
	public boolean takesArgument() {
		return false;
	}

	@Override
	public String toString() {
		return "x";
	}
}