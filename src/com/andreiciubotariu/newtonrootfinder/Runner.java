package com.andreiciubotariu.newtonrootfinder;

import java.util.ArrayList;

/**
 * @author Andrei Ciubotariu
 */
public class Runner {
	public static void main(String[] args) {
		Function f = new Function(new ArrayList<FunctionComponent>());
		f.addComponent((new Power((new X()), new Const(9.009))));

		System.out.println(f.computeFor(2));
		System.out.println(f.toString());
		System.out.println(f.computeFirstDerivative().toString());
		System.out.println(f.computeFirstDerivative().computeFor(2));
	}
}