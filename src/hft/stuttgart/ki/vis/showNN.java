package hft.stuttgart.ki.vis;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hft.stuttgart.ki.main.Netz;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
public class showNN {
	private int counter = 0;
	public JFrame frame;
	private int evo;
	private double error;
	private Drawer drawer;
	private JLabel evoLable;
	public Drawer getDrawer() {
		return drawer;
	}

	public showNN(Netz netz) {
		initialize(netz);
	}

	private void initialize(Netz netz) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawer = new Drawer(netz);
		 frame.getContentPane().add(drawer);
		 
		 JPanel panel = new JPanel();
		 frame.getContentPane().add(panel, BorderLayout.WEST);
		 GridBagLayout gbl_panel = new GridBagLayout();
		 gbl_panel.columnWidths = new int[]{0, 0};
		 gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		 gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		 gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		 panel.setLayout(gbl_panel);
		 evoLable = new JLabel(String.valueOf(this.evo));
		 JButton SchrittButton = new JButton(">>");
		 ArrayList<Integer> saver = new ArrayList<>();
		 SchrittButton.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 			netz.doForwardCalculation();
		 			netz.doBackwardCalculation();
		 			netz.doGewichteAktualisieren();
		 			if(((((AusgabeKnoten)((Schicht)netz.getNetz().get(netz.getNetz().size()-1)).getType().getKnoten().get(0).getType()).getYk() == 1) && (((AusgabeKnoten)((Schicht)netz.getNetz().get(netz.getNetz().size()-1)).getType().getKnoten().get(0).getType()).getOxVal() >= 1))) {
		 				saver.add(1);
		 			} else if(((((AusgabeKnoten)((Schicht)netz.getNetz().get(netz.getNetz().size()-1)).getType().getKnoten().get(0).getType()).getYk() == 0) && (((AusgabeKnoten)((Schicht)netz.getNetz().get(netz.getNetz().size()-1)).getType().getKnoten().get(0).getType()).getOxVal() < 1))) {
		 				saver.add(1);
		 			} else {
		 				saver.add(0);
		 			}
		 			try {
		 				
						if(!netz.selectRanData()) {
							System.out.println((double)saver.stream().mapToInt(n -> n).sum() / saver.size());
							netz.resetTemp();
							saver.clear();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

		 			evo++;
		 			evoLable.setText(String.valueOf(evo));
		 			counter = -1;
		 		counter++;
		 		drawer.repaint();
		 	}
		 });
		 GridBagConstraints gbc_SchrittButton = new GridBagConstraints();
		 gbc_SchrittButton.insets = new Insets(0, 0, 5, 0);
		 gbc_SchrittButton.gridx = 0;
		 gbc_SchrittButton.gridy = 0;
		 panel.add(SchrittButton, gbc_SchrittButton);
		 ArrayList<Double> valStat = new ArrayList<>();
		 
		 GridBagConstraints gbc_evoLable = new GridBagConstraints();
		 gbc_evoLable.insets = new Insets(0, 0, 5, 0);
		 gbc_evoLable.gridx = 0;
		 gbc_evoLable.gridy = 8;
		 panel.add(evoLable, gbc_evoLable);
	}

	public void aktual(int v) {
		evoLable.setText(String.valueOf(v));
	}
}

