package com.andreiciubotariu.newtonrootfinder;
import java.util.List;
import java.util.ArrayList; 

/**
 * @author Andrei Ciubotariu
 */
public class F extends Function{

	public F (Function arg){
		setArgument (arg);
	}

	@Override
	public double computeFor (double value){
		return Double.NaN;
	}

	@Override
	public Function computeFirstDerivative(){
		List <FunctionComponent> deriv = new ArrayList <FunctionComponent> ();
		deriv.add (getArgument().computeFirstDerivative());
		return new Function (deriv);
	}

	@Override
	public String displayString(){
		return "TEST ARG-ED FUNCTION";
	}

	@Override
	public Function sameType(){
		return new F(new Function(new ArrayList<FunctionComponent>()));
	}
	@Override
	public String toString(){
		return "F(" + getArgument().toString()+")";
	}
}