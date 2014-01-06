package com.andreiciubotariu.newtonrootfinder;

import java.util.ArrayList;
import java.util.List;

public class Sec extends Function {
	public Sec(Function arg) {
		setArgument(arg);
	}

	public Sec() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return 1 / Math.cos(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new Tan(getArgument()));
		deriv.add(Sign.MULT);
		deriv.add(new Sec(getArgument()));
		return new Function(deriv);
	}

	@Override
	public Function sameType() {
		return new Sec(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String displayString() {
		return "sec";
	}

	@Override
	public boolean hasComponents() {
		return true;
	}

	@Override
	public String toString() {
		return "sec(" + getArgument().toString() + ")";
	}
}
