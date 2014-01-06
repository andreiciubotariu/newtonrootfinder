package com.andreiciubotariu.newtonrootfinder;

/**
 * @author Andrei Ciubotariu
 */
public enum Sign implements FunctionComponent {
	PLUS, MIN, MULT, DIV;

	@Override
	public String toString() {
		switch (this) {
		case PLUS:
			return "+";
		case MIN:
			return "-";
		case MULT:
			return "*";
		case DIV:
			return "/";
		}
		return super.toString();
	}
}