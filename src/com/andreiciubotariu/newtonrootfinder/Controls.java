package com.andreiciubotariu.newtonrootfinder;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.border.*;
import java.util.*;

/**
 * @author Andrei Ciubotariu
 */
public class Controls {
	public interface ControlListener {
		public void onFunctionSet(Function f);

		public void showGraph();

		public void hideGraph();
	}

	private abstract class HideEquationListener implements ActionListener {
		@Override
		public final void actionPerformed(ActionEvent ae) {
			controlListener.hideGraph();
			action(ae);
		}

		protected abstract void action(ActionEvent ae);
	}

	private List<Function> currentFunction = new ArrayList<Function>();
	private final Function mainFunct = new Function(
			new ArrayList<FunctionComponent>());
	private JLabel currentFunctionDisplayLabel = new JLabel();
	private JLabel lowerFunctionDisplayLabel = new JLabel();
	private ControlListener controlListener;

	// number buttons
	private ActionListener constListener = new HideEquationListener() {

		@Override
		public void action(ActionEvent ae) {
			FunctionButton button = (FunctionButton) ae.getSource();
			Const newConst = (Const) button.getFunction().sameType();

			if (getCurrent().hasComponents()) {
				FunctionComponent f = getCurrent().lastComponent();
				if (f instanceof Power) {
					f = ((Power) f).getDegree();
					//((Power) f).setDegree(newConst.getValue());
					//printFunction();
					//return;
				} 
				/*else*/ if (f instanceof Const) {
					Const funct = (Const) f;
					String value = funct.displayString();
					String newValue = value += newConst.displayString();
					try {
						double d = Double.parseDouble(newValue);
						funct.setValue(d);
					} catch (NumberFormatException e) {
						
					}
					printFunction();
					return;
				}
			}

			if (getCurrent().hasComponents()
					&& getCurrent().lastComponent() instanceof Function) {
				getCurrent().addComponent(Sign.PLUS);
			}
			getCurrent().addComponent(newConst);
			printFunction();
		}
	};

	private JButton makeConstButton(double value) {
		JButton b = new FunctionButton(new Const(value));
		b.addActionListener(constListener);
		UIUtils.styleConstButton(b);
		return b;
	}

	// signs
	private ActionListener signListener = new HideEquationListener() {

		@Override
		public void action(ActionEvent ae) {
			if (getCurrent().hasComponents()) {
				if (getCurrent().lastComponent() instanceof Sign) {
					getCurrent().deleteLastComponent();
				}
				Sign s = ((SignButton) ae.getSource()).getSign();
				getCurrent().addComponent(s);
				printFunction();
			}
		}
	};

	private JButton makeSignButton(Sign s) {
		JButton b = new SignButton(s);
		b.addActionListener(signListener);
		UIUtils.styleControlButton(b);
		return b;
	}

	// functions
	private ActionListener functionListener = new HideEquationListener() {
		@Override
		public void action(ActionEvent ae) {
			Function f = ((FunctionButton) ae.getSource()).getFunction()
					.sameType();
			if (getCurrent().hasComponents()
					&& getCurrent().lastComponent() instanceof Function) {
				getCurrent().addComponent(Sign.PLUS);
			}
			getCurrent().addComponent(f);
			if (f.takesArgument()) {
				currentFunction.add(f.getArgument());
			}
			printFunction();
		}
	};

	private JButton makeFunctionButton(Function f) {
		JButton b = new FunctionButton(f);
		b.addActionListener(functionListener);
		UIUtils.styleFunctionButton(b);
		return b;
	}

	// rest of stuff
	private void printFunction() {
		currentFunctionDisplayLabel.setText(getCurrent().toString());
		if (currentFunction.size() > 1) {
			lowerFunctionDisplayLabel.setText(currentFunction.get(
					currentFunction.size() - 2).toString());
		} else {
			lowerFunctionDisplayLabel.setText(null);
		}
		System.out.println(mainFunct);
	}

	private Function getCurrent() {
		return currentFunction.get(currentFunction.size() - 1);
	}

	private void removeLast() {
		Function f = getCurrent();
		if (f.hasComponents() && f.lastComponent() instanceof Sign) {
			f.deleteLastComponent();
		}
		if (currentFunction.size() > 1) {
			currentFunction.remove(f);
		}
	}

	// instantiation of all function control components
	public JPanel makeFunctionInput(final ControlListener functListener) {
		controlListener = functListener;
		currentFunction.add(mainFunct);
		JPanel parent = new JPanel();
		parent.setBackground(Color.WHITE);
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		currentFunctionDisplayLabel.setBackground(Color.WHITE);
		JScrollPane currentFunctionScroller = new JScrollPane(
				currentFunctionDisplayLabel);
		currentFunctionScroller.getViewport().setBackground(Color.WHITE);
		parent.add(currentFunctionScroller);
		currentFunctionScroller.setPreferredSize(new Dimension(250, 50));

		JScrollPane functionScroller = new JScrollPane(
				lowerFunctionDisplayLabel);
		functionScroller.getViewport().setBackground(Color.WHITE);
		parent.add(functionScroller);
		functionScroller.setPreferredSize(new Dimension(250, 50));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new java.awt.GridLayout(0, 3, 10, 10));

		JPanel j = new JPanel();
		j.setBackground(Color.WHITE);
		j.setSize(250, 250);
		j.setLayout(new java.awt.GridLayout(0, 3, 2, 2));
		panel.add(j);

		for (int x = 1; x < 10; x++) {
			j.add(makeConstButton(x));
		}
		j.add(makeConstButton(0));
		j.add(makeConstButton(Math.E));
		j.add(makeConstButton(Math.PI));

		JPanel operations = new JPanel();
		operations.setBackground(Color.WHITE);
		// signs.setSize (250,250);
		operations.setLayout(new java.awt.GridLayout(0, 2, 2, 2));
		operations.add(makeSignButton(Sign.PLUS));
		operations.add(makeSignButton(Sign.MIN));
		operations.add(makeSignButton(Sign.MULT));
		operations.add(makeSignButton(Sign.DIV));

		JButton b = new JButton("^");
		b.addActionListener(new HideEquationListener() {
			@Override
			public void action(ActionEvent ae) {
				if (!getCurrent().hasComponents() || !getCurrent().isComplete()) {
					return;
				}
				Function f = (Function) getCurrent().lastComponent(); 
				if (!(f instanceof Const)) {
					getCurrent().deleteLastComponent();
					Function power = new Power(f, new Const(0));
					getCurrent().addComponent(power);
				} else {
					getCurrent().deleteLastComponent();
					Function exp = new Exp((Const) f, new Function(
							new ArrayList<FunctionComponent>()));
					getCurrent().addComponent(exp);
					if (exp.takesArgument()) {
						currentFunction.add(exp.getArgument());
					}
				}
				printFunction();
			}
		});
		UIUtils.styleControlButton(b);
		operations.add(b);

		b = new JButton("(-)");
		b.addActionListener(new HideEquationListener() {
			@Override
			public void action(ActionEvent ae) {
				if (getCurrent().hasComponents()) {
					FunctionComponent f = getCurrent().lastComponent();
					if (f instanceof Const) {
						Const funct = (Const) f;
						funct.setValue(funct.getValue() * -1);
						printFunction();
					}
				}
			}
		});
		UIUtils.styleControlButton(b);
		operations.add(b);

		b = new JButton(".");
		b.addActionListener(new HideEquationListener() {
			@Override
			public void action(ActionEvent ae) {
				if (getCurrent().hasComponents()) {
					FunctionComponent f = getCurrent().lastComponent();
					if (f instanceof Power){
						f = ((Power)f).getDegree();
					}
					if (f instanceof Const) {
						Const funct = (Const) f;
						funct.setHasDecimal(true);
						printFunction();
					}
				}
			}
		});
		UIUtils.styleControlButton(b);
		operations.add(b);

		panel.add(operations);

		JPanel modifiers = new JPanel(new java.awt.GridLayout(0, 2, 2, 2));
		modifiers.setBackground(Color.WHITE);
		b = new JButton(")");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (getCurrent().hasComponents()) {
					removeLast();
				}
				printFunction();
			}
		});
		UIUtils.styleImpButton(b);
		modifiers.add(b);

		b = new JButton("DEL");
		b.addActionListener(new HideEquationListener() {

			@Override
			public void action(ActionEvent ae) {
				if (getCurrent().hasComponents()) {
					getCurrent().deleteLastComponent();
				} else {
					removeLast();
					Function f = getCurrent();
					if (f.hasComponents() && f.lastComponent() instanceof Exp) {
						f.deleteLastComponent();
					}
				}
				printFunction();
			}
		});
		UIUtils.styleImpButton(b);
		modifiers.add(b);

		b = new JButton("DEL ALL");
		b.addActionListener(new HideEquationListener() {

			@Override
			public void action(ActionEvent ae) {
				currentFunction.get(0).removeAllComponents();

				for (int x = currentFunction.size() - 1; x >= 1; x--) {
					currentFunction.remove(x);
				}

				printFunction();
			}
		});
		UIUtils.styleImpButton(b);
		modifiers.add(b);

		b = new JButton("Update");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				printFunction();
				if (getCurrent().hasComponents() && getCurrent().isComplete()) {
					functListener.onFunctionSet(mainFunct);
					controlListener.showGraph();
				}
			}
		});
		UIUtils.styleEnterButton(b);
		modifiers.add(b);

		panel.add(modifiers);
		parent.add(panel);

		JPanel functions = new JPanel(new java.awt.GridLayout(0, 2, 2, 2));
		functions.setBackground(Color.WHITE);
		functions.setBorder(new EmptyBorder(10, 100, 10, 100));
		functions.setPreferredSize(new Dimension(100, 100));
		// functions.setSize (250,500);
		functions.add(makeFunctionButton(new X()));
		functions.add(makeFunctionButton(new Sin()));
		functions.add(makeFunctionButton(new Cos()));
		functions.add(makeFunctionButton(new Tan()));
		functions.add(makeFunctionButton(new SinH()));
		functions.add(makeFunctionButton(new CosH()));
		functions.add(makeFunctionButton(new TanH()));
		functions.add(makeFunctionButton(new Ln()));
		parent.add(functions);

		return parent;
	}
}