package hft.stuttgart.ki.vis;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import hft.stuttgart.ki.main.Netz;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class showNN {
	private int counter = 0;
	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					showNN window = new showNN(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public showNN(Netz netz) {
		initialize(netz);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Netz netz) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Drawer drawer = new Drawer(netz);
		 frame.getContentPane().add(drawer);
		 
		 JPanel panel = new JPanel();
		 frame.getContentPane().add(panel, BorderLayout.WEST);
		 GridBagLayout gbl_panel = new GridBagLayout();
		 gbl_panel.columnWidths = new int[]{0, 0};
		 gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		 gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		 gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		 panel.setLayout(gbl_panel);
		 
		 JButton SchrittButton = new JButton(">>");
		 SchrittButton.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		System.out.println(counter + " KLEIN");
		 		if(counter == 0) {
		 			netz.doForwardCalculation();
		 		} else if(counter == 1) {
		 			netz.doBackwardCalculation();
		 		} else if(counter == 2) {
		 			netz.doGewichteAktualisieren();
		 		} else {
		 			counter = 0;
		 		}
		 		counter++;
		 		drawer.repaint();
		 	}
		 });
		 GridBagConstraints gbc_SchrittButton = new GridBagConstraints();
		 gbc_SchrittButton.insets = new Insets(0, 0, 5, 0);
		 gbc_SchrittButton.gridx = 0;
		 gbc_SchrittButton.gridy = 0;
		 panel.add(SchrittButton, gbc_SchrittButton);
		 
		 JButton schleifeStartButton = new JButton("Schleife-Start");
		 schleifeStartButton.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		
		 	}
		 });
		 GridBagConstraints gbc_schleifeStartButton = new GridBagConstraints();
		 gbc_schleifeStartButton.insets = new Insets(0, 0, 5, 0);
		 gbc_schleifeStartButton.gridx = 0;
		 gbc_schleifeStartButton.gridy = 1;
		 panel.add(schleifeStartButton, gbc_schleifeStartButton);
		 
		 JButton schleifeStopButton = new JButton("Schleife-Stop");
		 schleifeStopButton.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		
		 	}
		 });
		 GridBagConstraints gbc_schleifeStopButton = new GridBagConstraints();
		 gbc_schleifeStopButton.gridx = 0;
		 gbc_schleifeStopButton.gridy = 2;
		 panel.add(schleifeStopButton, gbc_schleifeStopButton);
	}

}

