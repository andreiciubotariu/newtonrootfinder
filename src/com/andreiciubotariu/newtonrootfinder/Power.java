package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Power extends Function {
	private double mDeg;

	public Power(Function function, double deg) {
		setArgument(function);
		mDeg = deg;
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(new Const(mDeg));
		deriv.add(Sign.MULT);
		deriv.add(new Power(getArgument(), mDeg - 1));
		deriv.add(Sign.MULT);
		deriv.add(getArgument().computeFirstDerivative());
		return new Function(deriv);
	}

	@Override
	public String toString() {
		return "(" + getArgument().toString() + "^" + mDeg + ")";
	}

	@Override
	public Function sameType() {
		return new Power(new Function(new ArrayList<FunctionComponent>()), 0);
	}

	public void setDegree(double deg) {
		mDeg = deg;
	}

	@Override
	public double computeFor(double value) {
		return Math.pow(getArgument().computeFor(value), mDeg);
	}
}