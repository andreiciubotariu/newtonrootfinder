package com.andreiciubotariu.newtonrootfinder;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author Andrei Ciubotariu
 */
public class JFrameRunner {

	public static void main(String[] args) {
		JFrame f = new JFrame ("Newton-Raphson Root Finder");
		f.setPreferredSize(new Dimension (600,700));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Content c = new Content();
		c.init();
		f.add(c);
		f.pack();
		f.setVisible(true);
	}
}
