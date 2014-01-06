package com.andreiciubotariu.newtonrootfinder;

import java.util.ArrayList;
import java.util.List;

public class SecH extends Function {
	public SecH(Function arg) {
		setArgument(arg);
	}

	public SecH() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return 1 / Math.cosh(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new TanH(getArgument()));
		deriv.add(Sign.MULT);
		deriv.add(new Const(-1));
		deriv.add(Sign.MULT);
		deriv.add(new SecH(getArgument()));
		return new Function(deriv);
	}

	@Override
	public Function sameType() {
		return new SecH(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String displayString() {
		return "sech";
	}

	@Override
	public boolean hasComponents() {
		return true;
	}

	@Override
	public String toString() {
		return "sech(" + getArgument().toString() + ")";
	}
}
