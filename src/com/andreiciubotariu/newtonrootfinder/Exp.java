package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Exp extends Function {
	private Const mBase;

	public Exp(Const base, Function raisedTo) {
		mBase = base;
		setArgument(raisedTo);
	}

	@Override
	public double computeFor(double value) {
		return Math.pow(mBase.getValue(), getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(this);
		deriv.add(Sign.MULT);
		deriv.add(new Const(Math.log(mBase.getValue())));
		return new Function(deriv);
	}

	@Override
	public boolean hasComponents() {
		return getArgument().hasComponents();
	}

	@Override
	public boolean isComplete() {
		return getArgument().isComplete();
	}

	@Override
	public String toString() {
		return mBase.toString() + " ^(" + getArgument().toString() + ")";
	}
}