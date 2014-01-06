package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Sin extends Function {

	public Sin(Function arg) {
		setArgument(arg);
	}

	public Sin() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return Math.sin(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new Cos(getArgument()));
		return new Function(deriv);
	}

	@Override
	public Function sameType() {
		return new Sin(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String displayString() {
		return "sin";
	}

	@Override
	public boolean hasComponents() {
		return true;
	}

	@Override
	public String toString() {
		return "sin(" + getArgument().toString() + ")";
	}
}