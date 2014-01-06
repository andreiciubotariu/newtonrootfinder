package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class CosH extends Function {

	public CosH(Function arg) {
		setArgument(arg);
	}

	public CosH() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return Math.cosh(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new SinH(getArgument()));
		return new Function(deriv);
	}

	@Override
	public String displayString() {
		return "cosh";
	}

	@Override
	public Function sameType() {
		return new CosH(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String toString() {
		return "cosh(" + getArgument().toString() + ")";
	}
}