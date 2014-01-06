package com.andreiciubotariu.newtonrootfinder;

import javax.swing.JButton;

/**
 * @author Andrei Ciubotariu
 */
public class SignButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8234260301011276116L;
	private Sign mSign;

	public SignButton(Sign s) {
		String signText = "+";
		switch (s) {
		case MIN:
			signText = "-";
			break;
		case MULT:
			signText = "*";
			break;
		case DIV:
			signText = "/";
			break;
		case PLUS:
		default:
			break;

		}
		setText(signText);
		mSign = s;
	}

	public Sign getSign() {
		return mSign;
	}
}