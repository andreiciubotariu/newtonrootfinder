package com.andreiciubotariu.newtonrootfinder;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;

/**
 * @author Andrei Ciubotariu
 */
public class UIUtils {
	private enum Type {
		REGULAR, CONST, FUNCTION, CONTROL, IMPORTANT, ENTER
	}

	private static Color CONST_COLOR = new Color(0x0969A2);
	private static Color CONTROL_COLOR = new Color(0xFF8C00);
	private static Color IMP_COLOR = new Color(0xF60018);
	private static Color FUNCTION_COLOR = new Color(0xFFCB00);
	private static Color ENTER_COLOR = new Color(0x25D500);

	public static void styleRegButton(JButton b) {
		setButtonStyle(b, Type.REGULAR);
	}

	public static void styleConstButton(JButton b) {
		setButtonStyle(b, Type.CONST);
	}

	public static void styleControlButton(JButton b) {
		setButtonStyle(b, Type.CONTROL);
	}

	public static void styleImpButton(JButton b) {
		setButtonStyle(b, Type.IMPORTANT);
	}

	public static void styleFunctionButton(JButton b) {
		setButtonStyle(b, Type.FUNCTION);
	}

	public static void styleEnterButton(JButton b) {
		setButtonStyle(b, Type.ENTER);
	}

	private static void setButtonStyle(JButton b, Type type) {
		Color foregroundColor = null;
		Color backgroundColor = null;

		switch (type) {
		case CONST:
			foregroundColor = Color.WHITE;
			backgroundColor = CONST_COLOR;
			break;
		case CONTROL:
			foregroundColor = Color.WHITE;
			backgroundColor = CONTROL_COLOR;
			break;
		case IMPORTANT:
			foregroundColor = Color.WHITE;
			backgroundColor = IMP_COLOR;
			break;
		case FUNCTION:
			foregroundColor = Color.BLACK;
			backgroundColor = FUNCTION_COLOR;
			break;
		case ENTER:
			foregroundColor = Color.WHITE;
			backgroundColor = ENTER_COLOR;
			break;
		case REGULAR:
		default:
			foregroundColor = Color.BLACK;
			backgroundColor = Color.WHITE;
			break;

		}
		b.setForeground(foregroundColor);
		b.setBackground(backgroundColor);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		b.setBorder(compound);
	}

}