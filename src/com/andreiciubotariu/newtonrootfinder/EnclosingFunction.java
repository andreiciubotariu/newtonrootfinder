package com.andreiciubotariu.newtonrootfinder;

import java.util.ArrayList;

//display version of the general purpose class
public class EnclosingFunction extends Function {

	public EnclosingFunction(Function arg) {
		setArgument(arg);
	}

	public EnclosingFunction() {
		setArgument(null);
	}

	@Override
	public double computeFor(double value) {
		return getArgument().computeFor(value);
	}

	@Override
	public String displayString() {
		return "(...)";
	}

	@Override
	public Function sameType() {
		return new EnclosingFunction(new Function(new ArrayList<FunctionComponent>()));
	}

	@Override
	public String toString() {
		return "(" + getArgument().toString() + ")";
	}

}
