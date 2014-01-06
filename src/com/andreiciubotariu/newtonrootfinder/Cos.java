package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Cos extends Function {

	public Cos(Function arg) {
		setArgument(arg);
	}

	public Cos() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return Math.cos(getArgument().computeFor(value));
	}

	@Override
	public Function computeFirstDerivative() {
		List<FunctionComponent> deriv = new ArrayList<FunctionComponent>();
		deriv.add(getArgument().computeFirstDerivative());
		deriv.add(Sign.MULT);

		Function negSin = new Function(new ArrayList<FunctionComponent>());
		negSin.addComponent(new Const(-1));
		negSin.addComponent(Sign.MULT);
		negSin.addComponent(new Sin(getArgument()));
		deriv.add(negSin);
		return new Function(deriv);
	}

	@Override
	public String displayString() {
		return "cos";
	}

	@Override
	public Function sameType() {
		return new Cos(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String toString() {
		return "cos(" + getArgument().toString() + ")";
	}
}