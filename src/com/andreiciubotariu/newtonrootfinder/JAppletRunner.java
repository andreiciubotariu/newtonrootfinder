package com.andreiciubotariu.newtonrootfinder;

import javax.swing.JApplet;

/**
 * @author Andrei Ciubotariu
 */
public class JAppletRunner extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038871842603012463L;

	public void init() {
		super.init();
		Content c = new Content();
		c.init();
		add(c);
	}
}