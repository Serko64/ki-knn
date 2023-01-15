package hft.stuttgart.ki.vis;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import hft.stuttgart.ki.main.Netz;
import hft.stuttgart.ki.parts.Schicht.Ausgabe;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
public class showNN {
	private int counter = 0;
	public JFrame frame;
	private int evo;
	private double error;
	private JTable table;
	private JTextField schleifenLänge;
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
		 gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		 gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		 gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		 panel.setLayout(gbl_panel);
		 JLabel evoLable = new JLabel(String.valueOf(this.evo));
		 JLabel errorLable = new JLabel("0");
		 JButton SchrittButton = new JButton(">>");
		 ArrayList<Double[]> saver = new ArrayList<>();
		 SchrittButton.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		System.out.println(counter + " KLEIN");
		 		if(counter == 0) {
		 			System.err.println("WANT OPEN");
		 			netz.doForwardCalculation();
		 		} else if(counter == 1) {
		 			netz.doBackwardCalculation();
		 		} else if(counter == 2) {
		 			netz.doGewichteAktualisieren();
		 		} else if(counter == 3) {

		 			saver.add(new Double[] {((AusgabeKnoten)((Schicht)netz.getNetz().get(4)).getType().getKnoten().get(0).getType()).getYk(), ((AusgabeKnoten)((Schicht)netz.getNetz().get(4)).getType().getKnoten().get(0).getType()).getOxVal()});
		 			try {
		 				
						if(!netz.selectRanData()) {
							errorLable.setText(String.valueOf((double) saver.stream().mapToDouble(n ->{
								System.err.println(n[0] + " - "+ n[1] + " | " +(double) ((n[0]*n[0] - 2*n[1]*n[0] + n[1] * n[1])));
								return ((n[0]*n[0] - 2*n[1]*n[0] + n[1] * n[1]));
							}).sum()));
							System.err.println(saver.stream().mapToDouble(n ->{
								System.err.println(n[0] + " - "+ n[1] + " | " +(double) ((n[0]*n[0] - 2*n[1]*n[0] + n[1] * n[1])));
								return ((n[0]*n[0] - 2*n[1]*n[0] + n[1] * n[1]));
							}).sum());
							netz.resetTemp();
							saver.clear();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 		}else {
		 			evo++;
		 			evoLable.setText(String.valueOf(evo));
		 			counter = -1;
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
		 schleifenLänge = new JTextField();
		 JButton schleifeStartButton = new JButton("Schleife-Start");
		 ArrayList<Double> valStat = new ArrayList<>();
		 schleifeStartButton.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
					for(int i = 0;i<Integer.parseInt(schleifenLänge.getText());i++) {
						netz.doForwardCalculation();
						netz.doBackwardCalculation();
						netz.doGewichteAktualisieren();
						saver.add(new Double[] {((AusgabeKnoten)((Schicht)netz.getNetz().get(4)).getType().getKnoten().get(0).getType()).getYk(), ((AusgabeKnoten)((Schicht)netz.getNetz().get(4)).getType().getKnoten().get(0).getType()).getOxVal()});
						try {
							if(!netz.selectRanData()) {
								valStat.add(saver.stream().mapToDouble(n ->{
									System.err.println(n[0] + " - "+ n[1] + " | " +(double) ((n[0]*n[0] - 2*n[1]*n[0] + n[1] * n[1])));
									return ((n[0]*n[0] - 2*n[1]*n[0] + n[1] * n[1]));
								}).sum());
								netz.resetTemp();
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						drawer.repaint();
					}
					for (Double double1 : valStat) {
						System.out.print((1/2)*double1);
					}
					System.out.println(valStat);
		 	}
		 });
		 GridBagConstraints gbc_schleifeStartButton = new GridBagConstraints();
		 gbc_schleifeStartButton.insets = new Insets(0, 0, 5, 0);
		 gbc_schleifeStartButton.gridx = 0;
		 gbc_schleifeStartButton.gridy = 1;
		 panel.add(schleifeStartButton, gbc_schleifeStartButton);
		 
		 GridBagConstraints gbc_schleifenLänge = new GridBagConstraints();
		 gbc_schleifenLänge.insets = new Insets(0, 0, 5, 0);
		 gbc_schleifenLänge.fill = GridBagConstraints.HORIZONTAL;
		 gbc_schleifenLänge.gridx = 0;
		 gbc_schleifenLänge.gridy = 3;
		 panel.add(schleifenLänge, gbc_schleifenLänge);
		 schleifenLänge.setColumns(10);
		 
		 GridBagConstraints gbc_evoLable = new GridBagConstraints();
		 gbc_evoLable.insets = new Insets(0, 0, 5, 0);
		 gbc_evoLable.gridx = 0;
		 gbc_evoLable.gridy = 8;
		 panel.add(evoLable, gbc_evoLable);
		 
		 GridBagConstraints gbc_errorLable = new GridBagConstraints();
		 gbc_errorLable.gridx = 0;
		 gbc_errorLable.gridy = 9;
		 panel.add(errorLable, gbc_errorLable);
		 
		 JPanel panel_1 = new JPanel();
		 frame.getContentPane().add(panel_1, BorderLayout.EAST);
		 
		 table = new JTable();
		 panel_1.add(table);
	}

	public int getEvo() {
		return evo;
	}

}

