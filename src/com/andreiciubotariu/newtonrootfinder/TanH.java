package com.andreiciubotariu.newtonrootfinder;

import java.util.ArrayList;
import java.util.List;

public class TanH extends Function {

	public TanH(Function arg) {
		setArgument(arg);
	}

	public TanH() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return Math.tanh(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new Power(new SecH(getArgument()), 2));
		return new Function(deriv);
	}

	@Override
	public Function sameType() {
		return new TanH(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String displayString() {
		return "tanh";
	}

	@Override
	public boolean hasComponents() {
		return true;
	}

	@Override
	public String toString() {
		return "tanh(" + getArgument().toString() + ")";
	}

}
