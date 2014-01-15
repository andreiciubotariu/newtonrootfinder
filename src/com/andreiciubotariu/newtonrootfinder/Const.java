package com.andreiciubotariu.newtonrootfinder;

import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * @author Andrei Ciubotariu
 */
public class Const extends Function {
	private double mValue;
	private boolean mHasDecimal;
	private boolean mIgnoreLastDigit;
	private NumberFormat nf = new DecimalFormat("##.###");

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
	
	public boolean hasDecimal(){
		return mHasDecimal;
	}
	
	//dependent on mHasDecimal
	private int trailingZeroes = 0;
	public void setTrailingZeroes(int trailingZeroes){
		this.trailingZeroes = trailingZeroes;
	}
	
	public int getTrailingZeroes(){
		return trailingZeroes;
	}
	
	public void ignoreLastDigit(boolean ignore){
		mIgnoreLastDigit = ignore;
	}
	
	public boolean ignoringLastDigit(){
		return mIgnoreLastDigit;
	}

	private String getStringForAbsValue(double value) {
		double absValue = Math.abs(value);
		if (absValue == Math.E)
			return "e";
		else if (absValue == Math.PI)
			return "pi";
			//return "π";

		String formatted = nf.format(absValue);
		if (mHasDecimal && !formatted.contains("."))
			formatted += ".";
		if (mHasDecimal){
			int availableDigits = 3-(formatted.length()-(formatted.indexOf(".")+1));
			for (int x = 0; x< trailingZeroes && x<availableDigits;x++){
				System.out.println ("Wrote a zero");
				formatted+="0";
			}
		}
		System.out.println ("Actual value: " + Double.parseDouble(formatted));
		return formatted;
	}

	@Override
	public String toString() {
		return "(" + displayString() + ")";
	}
}