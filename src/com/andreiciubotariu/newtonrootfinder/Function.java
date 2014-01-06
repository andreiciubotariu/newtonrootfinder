package com.andreiciubotariu.newtonrootfinder;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Function implements FunctionComponent {
	private List<FunctionComponent> mFunctionTerms;

	public Function(List<FunctionComponent> func) {
		mFunctionTerms = func;
	}

	public Function() {
	}

	public void addComponent(FunctionComponent comp) {
		if (mFunctionTerms == null) {
			throw new IllegalStateException(
					"mFunctionTerms is null - Are you actually trying to make a non-standard function?");
		}
		mFunctionTerms.add(comp);
	}

	public void removeAllComponents() {
		if (mFunctionTerms != null)
			mFunctionTerms.clear();
	}

	public Function computeFirstDerivative() {
		if (mFunctionTerms == null) {
			throw new IllegalStateException(
					"mFunctionTerms is null - Did you forget to override computeFirstDerivative()?");
		}

		int termSize = mFunctionTerms.size();
		Function deriv = new Function(new ArrayList<FunctionComponent>());
		List<Function> temp = new ArrayList<Function>();
		temp.add((Function) mFunctionTerms.get(0));
		for (int x = 1; x + 1 < termSize; x += 2) {
			if (mFunctionTerms.get(x) instanceof Sign) {
				Function f = (Function) mFunctionTerms.get(x + 1);
				switch ((Sign) mFunctionTerms.get(x)) {
				case PLUS:
				case MIN:
					if (temp.size() > 1) {
						deriv.addComponent(productRule(temp));
					} else {
						deriv.addComponent(temp.get(0).computeFirstDerivative());
					}
					deriv.addComponent(mFunctionTerms.get(x));
					temp = new ArrayList<Function>();
					temp.add(f);
					break;
				case MULT:
					temp.add(f);
					break;
				case DIV:
					temp.add(new Power(f, -1));
					break;
				}
			}
		}
		if (temp.size() > 1) {
			deriv.addComponent(productRule(temp));
		} else {
			deriv.addComponent(temp.get(0).computeFirstDerivative());
		}
		return deriv;
	}

	private static Function productRule(List<Function> temp) {
		Function deriv = new Function(new ArrayList<FunctionComponent>());
		for (int x = 0; x < temp.size(); x++) {
			deriv.addComponent(temp.get(x).computeFirstDerivative());
			for (int y = 0; y < temp.size(); y++) {
				if (y != x) {
					deriv.addComponent(Sign.MULT);
					deriv.addComponent(temp.get(y));
				}
			}
			if (x != temp.size() - 1) {
				deriv.addComponent(Sign.PLUS);
			}
		}
		return deriv;
	}

	private static double multiplication(List<Function> temp, double value) {
		if (temp.size() == 0) {
			return 0;
		}
		double result = 1;
		for (int x = 0; x < temp.size(); x++) {
			result *= temp.get(x).computeFor(value);
		}
		return result;
	}

	public double computeFor(double value) {
		if (mFunctionTerms == null) {
			throw new IllegalStateException(
					this.getClass().toString()
							+ "mFunctionTerms is null - Did you forget to override computeFor()?");
		}

		int termSize = mFunctionTerms.size();
		double result = 0;
		List<Function> temp = new ArrayList<Function>();
		temp.add((Function) mFunctionTerms.get(0));
		for (int x = 1; x + 1 < termSize; x += 2) {
			if (mFunctionTerms.get(x) instanceof Sign) {
				Function f = (Function) mFunctionTerms.get(x + 1);
				switch ((Sign) mFunctionTerms.get(x)) {
				case PLUS:
					if (temp.size() > 1) {
						result += multiplication(temp, value);
					} else {
						result += temp.get(0).computeFor(value);
					}
					temp = new ArrayList<Function>();
					temp.add(f);
					break;
				case MIN:
					if (temp.size() > 1) {
						result += multiplication(temp, value);
					} else {
						result += temp.get(0).computeFor(value);
					}
					temp = new ArrayList<Function>();
					temp.add(new Const(-1));
					temp.add(f);
					break;
				case MULT:
					temp.add(f);
					break;
				case DIV:
					temp.add(new Power(f, -1));
					break;
				}
			}
		}
		if (temp.size() > 1) {
			result += multiplication(temp, value);
		} else {
			result += temp.get(0).computeFor(value);
		}
		return result;
	}

	@Override
	public String toString() {
		String s = "";
		for (int x = 0; x < mFunctionTerms.size() - 1; x++) {
			s += mFunctionTerms.get(x).toString() + " ";
		}
		if (mFunctionTerms.size() > 0) {
			s += mFunctionTerms.get(mFunctionTerms.size() - 1).toString();
		}
		return s;
	}

	public String displayString() {
		return null;
	}

	public Function sameType() {
		return new Function(new ArrayList<FunctionComponent>());
	}

	public void setArgument(Function f) {
		if (mFunctionTerms != null) {
			mFunctionTerms.clear();
		} else {
			mFunctionTerms = new ArrayList<FunctionComponent>();
		}
		mFunctionTerms.add(f);
	}

	public void deleteLastComponent() {
		mFunctionTerms.remove(mFunctionTerms.size() - 1);
	}

	public boolean isComplete() {
		return mFunctionTerms.size() != 0
				&& !(mFunctionTerms.get(mFunctionTerms.size() - 1) instanceof Sign);
	}

	public boolean hasComponents() {
		if (mFunctionTerms.size() == 0) {
			return false;
		}

		return mFunctionTerms.get(0) instanceof Function;
	}

	public FunctionComponent lastComponent() {
		return mFunctionTerms.get(mFunctionTerms.size() - 1);
	}

	public boolean takesArgument() {
		return true;
	}

	public Function getArgument() {
		return (Function) mFunctionTerms.get(0);
	}
}