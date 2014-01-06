package com.andreiciubotariu.newtonrootfinder;

import javax.swing.JButton;

/**
 * @author Andrei Ciubotariu
 */
public class FunctionButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6293519430582036739L;
	private Function mFunction;

	public FunctionButton(Function f) {
		super(f.displayString());
		mFunction = f;
	}

	public Function getFunction() {
		return mFunction.sameType();
	}
}