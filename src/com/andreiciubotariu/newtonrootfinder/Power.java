package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Power extends Function {
	private Const mDeg;

	public Power(Function function, Const deg) {
		setArgument(function);
		mDeg = deg;
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(new Const(mDeg.getValue()));
		deriv.add(Sign.MULT);
		deriv.add(new Power(getArgument(), new Const(mDeg.getValue() - 1)));
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
		return new Power(new Function(new ArrayList<FunctionComponent>()), new Const(0));
	}

	public void setDegreeValue(double deg) {
		mDeg.setValue(deg);
	}
	
	public double getDegreeValue(){
		return mDeg.getValue();
	}
	
	public Const getDegree(){
		return mDeg;
	}

	@Override
	public double computeFor(double value) {
		return Math.pow(getArgument().computeFor(value), mDeg.getValue());
	}
}