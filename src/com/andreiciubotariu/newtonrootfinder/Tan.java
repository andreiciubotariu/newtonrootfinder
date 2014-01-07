package com.andreiciubotariu.newtonrootfinder;

import java.util.ArrayList;
import java.util.List;

public class Tan extends Function {

	public Tan(Function arg) {
		setArgument(arg);
	}

	public Tan() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return Math.tan(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new Power(new Sec(getArgument()), new Const(2)));
		return new Function(deriv);
	}

	@Override
	public Function sameType() {
		return new Tan(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String displayString() {
		return "tan";
	}

	@Override
	public boolean hasComponents() {
		return true;
	}

	@Override
	public String toString() {
		return "tan(" + getArgument().toString() + ")";
	}
}
