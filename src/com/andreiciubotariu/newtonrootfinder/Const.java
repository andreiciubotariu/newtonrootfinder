package com.andreiciubotariu.newtonrootfinder;

import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * @author Andrei Ciubotariu
 */
public class Const extends Function {
	private double mValue;
	private boolean mHasDecimal;

	public Const(double coeff) {
		mValue = coeff;
	}

	@Override
	public double computeFor(double value) {
		return mValue;
	}

	@Override
	public Function computeFirstDerivative() {
		return new Const(0);
	}

	public double getValue() {
		return mValue;
	}

	public void setValue(double value) {
		mValue = value;
	}

	@Override
	public String displayString() {
		return (mValue < 0 ? "-" : "") + getStringForAbsValue(mValue);
	}

	@Override
	public Function sameType() {
		return new Const(mValue);
	}

	@Override
	public boolean takesArgument() {
		return false;
	}

	public void setHasDecimal(boolean hasDecimal) {
		mHasDecimal = hasDecimal;
	}

	private String getStringForAbsValue(double value) {
		double absValue = Math.abs(value);
		if (absValue == Math.E)
			return "e";
		else if (absValue == Math.PI)
			return "pi";
			//return "π";

		NumberFormat nf = new DecimalFormat("##.###");
		String formatted = nf.format(absValue);
		if (mHasDecimal && !formatted.contains("."))
			formatted += ".";
		return formatted;
	}

	@Override
	public String toString() {
		return "(" + displayString() + ")";
	}
}