package com.andreiciubotariu.newtonrootfinder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @author Andrei Ciubotariu
 */
public class Content extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038871842603012463L;
	private GraphViewer graphViewer;

	public void init() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		graphViewerInit();
		otherInit();
		JPanel j = new JPanel();
		j.setBackground(Color.WHITE);
		j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
		j.add(graphViewer);
		JLabel name = new JLabel(
				"<html><div align=right>Andrei Ciubotariu, 2013/2014</div></html>");
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		j.add(name);

		add(j, BorderLayout.CENTER);
		addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				validate();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}
		});

	}

	private void otherInit() {
		JPanel holder = new JPanel();
		holder.setBackground(Color.WHITE);
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Newton-Raphson Root Finder");
		title.setFont(new Font(title.getFont().getName(), Font.BOLD, 16));
		JPanel j = new JPanel();
		j.setBackground(Color.WHITE);
		j.add(title);
		holder.add(j);
		Controls t = new Controls();
		holder.add(t.makeFunctionInput(new Controls.ControlListener() {
			@Override
			public void onFunctionSet(Function f) {
				graphViewer.setFunction(f);
			}

			@Override
			public void hideGraph() {
				graphViewer.setShowGraph(false);
			}

			@Override
			public void showGraph() {
				graphViewer.setShowGraph(true);
			}
		}));

		JPanel rootControlPanel = new JPanel();
		rootControlPanel.setBackground(Color.WHITE);
		JLabel roots = new JLabel("Roots:");

		final JTextField startField = new JTextField("0.0");


		startField.setPreferredSize(new Dimension(50, 20));
		rootControlPanel.add(roots);

		rootControlPanel.add(new JLabel("Starting X value"));
		rootControlPanel.add(startField);
		graphViewer.setInitialStartField(startField);
		JLabel iterationsLabel =new JLabel("Iterations");
		iterationsLabel.setToolTipText("Max " + GraphViewer.MAX_ITERATIONS + " iterations");
		rootControlPanel.add(iterationsLabel);

		final JTextField iterationsField = new JTextField("0");
		iterationsField.setPreferredSize(new Dimension(50, 20));
		rootControlPanel.add(iterationsField);
		graphViewer.setIterationsField(iterationsField);

		ActionListener rootListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double initialStart = graphViewer.getInitialStart();
				try {
					initialStart = Double.parseDouble(startField
							.getText());
				} catch (NumberFormatException ne) {
				}
				graphViewer.setInitialStart(initialStart);

				int iterations = graphViewer.getIterations();
				try {
					iterations = Integer.parseInt(iterationsField
							.getText());
				} catch (NumberFormatException ne) {
				}
				graphViewer.setIterations(iterations);
				graphViewer.findRoot();
				graphViewer.repaint();
			}
		};

		startField.addActionListener(rootListener);
		iterationsField.addActionListener(rootListener);

		JButton findRootButton = new JButton("Find a root");
		findRootButton.addActionListener(rootListener);
		rootControlPanel.add(findRootButton);
		UIUtils.styleRegButton(findRootButton);
		holder.add(rootControlPanel);

		JPanel zoomControls = new JPanel();
		zoomControls.setBackground(Color.WHITE);

		final JTextField[] zoomInputs = new JTextField[4];
		String[] labels = { "xMin", "xMax", "yMin", "yMax" };

		ActionListener windowControlListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double[] controls = { 
						graphViewer.getXMin(),
						graphViewer.getXMax(), 
						graphViewer.getYMin(),
						graphViewer.getYMax() 
				};
				for (int x = 0; x < 4; x++) {
					try {
						controls[x] = Double.parseDouble(zoomInputs[x]
								.getText());
					} catch (NumberFormatException ne) {
						// ne.printStackTrace();
					}
				}
				graphViewer.setXMin(controls[0]);
				graphViewer.setXMax(controls[1]);
				graphViewer.setYMin(controls[2]);
				graphViewer.setYMax(controls[3]);
				graphViewer.repaint();
			}
		};

		for (int x = 0; x < 4; x++) {
			JLabel label = new JLabel(labels[x]);
			zoomControls.add(label);

			zoomInputs[x] = new JTextField(3);
			zoomControls.add(zoomInputs[x]);
			zoomInputs[x].addActionListener(windowControlListener);
			graphViewer.setZoomInput(zoomInputs[x], x);
		}	
		graphViewer.setXMin(-10);
		graphViewer.setXMax(10);
		graphViewer.setYMin(-10);
		graphViewer.setYMax(10);
		JButton setZoomButton = new JButton("Set window");
		setZoomButton.addActionListener(windowControlListener);
		zoomControls.add(setZoomButton);
		UIUtils.styleRegButton(setZoomButton);
		JButton resetWindowButton = new JButton("Reset window");
		resetWindowButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				graphViewer.setXMin(-10);
				graphViewer.setXMax(10);
				graphViewer.setYMin(-10);
				graphViewer.setYMax(10);
				graphViewer.repaint();
			}

		});
		zoomControls.add(resetWindowButton);
		UIUtils.styleRegButton(resetWindowButton);
		holder.add(zoomControls);

		this.add(holder, BorderLayout.PAGE_START);
	}

	private void graphViewerInit() {
		graphViewer = new GraphViewer();
		graphViewer.setBackground(Color.WHITE);
		Function quadratic = new Function(new ArrayList<FunctionComponent>());
		quadratic.addComponent(new Power(new X(), new Const(2)));
		quadratic.addComponent(Sign.PLUS);

		quadratic.addComponent(new Const(2));
		quadratic.addComponent(Sign.MULT);
		quadratic.addComponent(new X());
		quadratic.addComponent(Sign.PLUS);

		quadratic.addComponent(new Const(1));

		graphViewer.setFunction(quadratic);
	}
}