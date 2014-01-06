package com.andreiciubotariu.newtonrootfinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * @author Andrei Ciubotariu
 */
public class GraphViewer extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7018285951172715167L;
	private double xMin = -10, xMax = 10, yMin = -10, yMax = 10;
	private Function eqn;
	private Function deriv;
	private double initialStart = 0;

	private int[] leftButtonCoords = { -1, -1 };
	private int[] rightButtonCoords = { -1, -1 };

	private double root = Double.NaN;
	private int iterations = 0;
	private JTextField startField;
	private JTextField iterationsField;
	private NumberFormat nf = new DecimalFormat("##.###");

	// [xMin,xMax,yMin,yMax]
	private JTextField[] minMaxTextFields = new JTextField[4];
	private RootFinder rootFinderWorker;

	private boolean showGraph = false;

	private java.util.List<Point2D.Double> tangentPoints = new ArrayList<Point2D.Double>();

	private class RootFinder extends SwingWorker<Void, Void> {
		public RootFinder() {

		}

		@Override
		public Void doInBackground() {
			if (deriv == null || eqn == null || iterations == 0) {
				return null;
			}
			int counter = 0;
			double currentX = initialStart;
			double newX = 0;

			if (eqn.computeFor(currentX) == 0) {
				root = currentX;
				return null;
			}
			while (true) {
				newX = currentX
						- (eqn.computeFor(currentX) / deriv
								.computeFor(currentX));
				tangentPoints.add(new Point2D.Double(currentX, eqn
						.computeFor(currentX)));
				// drawTangent (g,currentX, eqn.computeFor (currentX));
				if (counter == iterations - 1 || eqn.computeFor(newX) == 0
						|| Math.abs(currentX - newX) < 0.0001
						|| eqn.computeFor(currentX) == eqn.computeFor(newX)) {
					System.out.println("Root at " + nf.format(newX));
					root = newX;
					break;
				}
				currentX = newX;
				counter++;
			}
			return null;
		}

		@Override
		public void done() {
			repaint();
		}
	}

	public GraphViewer() {
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int notches = e.getWheelRotation();
				if (notches < 0) {
					setXMin(xMin + 1);
					setXMax(xMax - 1);
					setYMin(yMin + 1);
					setYMax(yMax - 1);
				} else {
					setXMin(xMin - 1);
					setXMax(xMax + 1);
					setYMin(yMin - 1);
					setYMax(yMax + 1);
				}
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					leftButtonCoords[0] = e.getX();
					leftButtonCoords[1] = e.getY();
					startField.setText(nf.format(toRegX(leftButtonCoords[0])));
				} else {
					int deltaX = (e.getX() - rightButtonCoords[0]) / 10;
					int deltaY = (e.getY() - rightButtonCoords[1]) / 10;
					setXMin(xMin - deltaX);
					setXMax(xMax - deltaX);
					setYMin(yMin + deltaY);
					setYMax(yMin + deltaY);

					rightButtonCoords[0] = e.getX();
					rightButtonCoords[1] = e.getY();
				}
				repaint();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("mousePressed");
				if (SwingUtilities.isLeftMouseButton(e)) {
					leftButtonCoords[0] = e.getX();
					leftButtonCoords[1] = e.getY();
					startField.setText(nf.format(toRegX(leftButtonCoords[0])));
				} else {
					rightButtonCoords[0] = e.getX();
					rightButtonCoords[1] = e.getY();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					System.out.println("mouseReleased");
					initialStart = toRegX(leftButtonCoords[0]);
					repaint();
				} else {
					rightButtonCoords[0] = -1;
					rightButtonCoords[1] = -1;
				}
			}
		});
	}

	public void setShowGraph(boolean show) {
		showGraph = show;
	}

	public void setFunction(Function e) {
		tangentPoints.clear();
		if (e.isComplete()) {
			eqn = e;
			// System.out.println (eqn.toString());
			deriv = e.computeFirstDerivative();
		} else {
			eqn = null;
			deriv = null;
		}
		repaint();
	}

	public void setXMin(double xMin) {
		if (xMin < xMax) {
			this.xMin = xMin;
		}
		updateTextField(0, this.xMin);
	}

	public void setXMax(double xMax) {
		if (xMax > xMin) {
			this.xMax = xMax;
		}
		updateTextField(1, this.xMax);
	}

	public void setYMin(double yMin) {
		if (yMin < yMax) {
			this.yMin = yMin;
		}
		updateTextField(2, this.yMin);
	}

	public void setYMax(double yMax) {
		if (yMax > yMin) {
			this.yMax = yMax;
		}
		updateTextField(3, this.yMax);
	}

	public void setZoomInput(JTextField textField, int which) {
		minMaxTextFields[which] = textField;
	}

	public void setInitialStartField(JTextField startField) {
		this.startField = startField;
	}

	public void setIterationsField(JTextField iterationsField) {
		this.iterationsField = iterationsField;
	}

	public void findRoot() {
		tangentPoints.clear();
		if (rootFinderWorker == null || rootFinderWorker.isCancelled()
				|| rootFinderWorker.isDone()) {
			rootFinderWorker = new RootFinder();
			rootFinderWorker.execute();
		}
	}

	private void updateTextField(int which, double value) {
		if (minMaxTextFields[which] != null) {
			minMaxTextFields[which].setText(String.valueOf(value));
		}
	}

	public void setInitialStart(double start) {
		initialStart = start;
	}

	public void setIterations(int iterations) {
		if (iterations < 50) {
			this.iterations = iterations;
		}
		iterationsField.setText(String.valueOf(iterations));
		root = Double.NaN;
	}

	private double toRegX(double xCoor) {
		return xCoor * (xMax - xMin) / getWidth() + xMin;
	}

	// translate coordinates to JPanel's coordinate system.
	private double toPanelX(double xCoor) {
		return (xCoor - xMin) * getWidth() / (xMax - xMin);
	}

	private double toPanelY(double yCoor) {
		return ((yMax - yCoor)) * getHeight() / (yMax - yMin);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, getWidth(), 0);
		// g.setColor (Color.WHITE);
		// g.fillRect (0,0,getWidth(),getHeight());
		g.setColor(Color.GRAY);
		// graph axes
		drawXAxis(g);
		g.drawLine((int) toPanelX(xMin), (int) toPanelY(0),
				(int) toPanelX(xMax), (int) toPanelY(0));
		g.drawLine((int) toPanelX(0), (int) toPanelY(yMin), (int) toPanelX(0),
				(int) toPanelY(yMax));

		// equation and tangents
		if (!showGraph) {
			return;
		}
		g.setColor(Color.RED);
		drawFunction(g);
		g.setColor(Color.BLUE);
		computeTangents(g);
		g.setColor(Color.GREEN);
		g.drawString(nf.format(toRegX(leftButtonCoords[0])),
				leftButtonCoords[0], leftButtonCoords[1]);
		g.setColor(Color.RED);
		if (!Double.isNaN(root)) {
			g.drawString("One root is at x=" + nf.format(root), 0,
					getHeight() - 10);
		}
	}

	public void drawXAxis(Graphics g) {
		for (double x = xMin; x < xMax; x += ((xMax - xMin) / 10)) {
			g.drawLine((int) toPanelX(x), (int) toPanelY((yMax - yMin) / 60),
					(int) toPanelX(x), (int) toPanelY(-((yMax - yMin) / 60)));
			g.drawString(nf.format(x), (int) toPanelX(x), (int) toPanelY(0));
		}
		for (double y = yMin; y < yMax; y += ((yMax - yMin) / 10)) {
			g.drawLine((int) toPanelX((xMax - xMin) / 80), (int) toPanelY(y),
					(int) toPanelX(-(xMax - xMin) / 80), (int) toPanelY(y));
			g.drawString(nf.format(y), (int) toPanelX(0), (int) toPanelY(y));
		}
	}

	private void drawFunction(Graphics g) {
		if (eqn == null) {
			return;
		}
		double step = (xMax - xMin) / 100;
		if (step > 0.1)
			step = 0.1;
		double prevX = xMin;
		double prevY = eqn.computeFor(prevX);
		for (double x = Math.floor(xMin + step); x <= xMax; x += step) {
			double currentY = (eqn.computeFor(x));
			// if ((!Double.isInfinite(prevY) && !Double.isNaN(prevY)) &&
			// (!Double.isInfinite(currentY) && !Double.isNaN(currentY))){
			g.drawLine((int) toPanelX(prevX), (int) toPanelY(prevY),
					(int) toPanelX(x), (int) toPanelY(currentY));
			// }
			prevX = x;
			prevY = currentY;
		}
	}

	private void computeTangents(final Graphics g) {
		for (Point2D.Double p : tangentPoints) {
			drawTangent(g, p.x, p.y);
		}

	}

	private void drawTangent(Graphics g, double x, double y) {
		double derivSlope = deriv.computeFor(x);
		Function tangent = new Function(new ArrayList<FunctionComponent>());
		tangent.addComponent(new Const(derivSlope));
		tangent.addComponent(Sign.MULT);
		tangent.addComponent(new X());
		tangent.addComponent(Sign.PLUS);
		tangent.addComponent(new Const(-derivSlope * x + y));
		g.drawLine((int) toPanelX(xMin),
				(int) toPanelY(tangent.computeFor(xMin)), (int) toPanelX(xMax),
				(int) toPanelY(tangent.computeFor(xMax)));
	}
}