package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class SinH extends Function {

	public SinH(Function arg) {
		setArgument(arg);
	}

	public SinH() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return Math.sinh(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new CosH(getArgument()));
		return new Function(deriv);
	}

	@Override
	public Function sameType() {
		return new SinH(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String displayString() {
		return "sinh";
	}

	@Override
	public boolean hasComponents() {
		return true;
	}

	@Override
	public String toString() {
		return "sinh(" + getArgument().toString() + ")";
	}
}