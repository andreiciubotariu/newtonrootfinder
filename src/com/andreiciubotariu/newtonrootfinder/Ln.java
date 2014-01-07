package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Ln extends Function {

	public Ln(Function arg) {
		setArgument(arg);
	}

	public Ln() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return Math.log(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);
		deriv.add(new Power(getArgument(), new Const(-1)));
		return new Function(deriv);
	}

	@Override
	public String displayString() {
		return "ln";
	}

	@Override
	public Function sameType() {
		return new Ln(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String toString() {
		return "ln(" + getArgument().toString() + ")";
	}
}