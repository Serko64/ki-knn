package hft.stuttgart.ki.vis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

import hft.stuttgart.ki.main.Netz;
import hft.stuttgart.ki.parts.Schicht.Ausgabe;
import hft.stuttgart.ki.parts.Schicht.Hidden;
import hft.stuttgart.ki.parts.Schicht.KantenSchicht;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.AusgabeKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.HiddenKnoten;
import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;

public class Drawer extends JComponent {
	private int oneKnoX = 80;
	private int oneKnoY = 40;
	private int staKnoX = 120;
	private int staKnoY = 200;
	private final int KnoW = 40;
	private final int KnoH = 40;
	private final int LineLXAdd = 40;
	private final int LineLYAdd = 20;
	private final int LineRYAdd = 20;
	private ArrayList<Object> netz;
	private Netz n;
	private static final long serialVersionUID = 2471902300638714690L;

	public Drawer(Netz netz) {
		super();
		this.netz = netz.getNetz();
		this.n = netz;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, 1000, 1000);
		drawNetz(this.netz, g);
	}
	public void drawNetz(ArrayList<Object> netz, Graphics g) {
		Font font = new Font("Arial",Font.BOLD,20);
		g.setFont(font);
		int kk = 0;
		for(int i=0;i<netz.size();i++) {
			if(i%2 == 0) {
				int counter = 0;
				for (SchichtKomponent kN:((Schicht)netz.get(i)).getType().getKnoten()) {
					if(((KnotenTyp)kN.getType()).isBias()) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLACK);
					}
					g.fillOval(oneKnoX+staKnoX*i, oneKnoY+staKnoY*counter, KnoH, KnoW);
					g.setColor(Color.BLUE);
					g.drawString(String.valueOf(((KnotenTyp)kN.getType()).getIxVal()), oneKnoX+staKnoX*i, oneKnoY+staKnoY*counter);
					g.drawString(String.valueOf(((KnotenTyp)kN.getType()).getOxVal()), oneKnoX+staKnoX*i, oneKnoY+50+staKnoY*counter);
					g.setColor(Color.MAGENTA);
					if(kN.getType().getClass().equals(HiddenKnoten.class)) {
						g.drawString(String.valueOf(((HiddenKnoten)kN.getType()).getDeltaJ()), oneKnoX+staKnoX*i, oneKnoY+25+staKnoY*counter);
					}else if(kN.getType().getClass().equals(AusgabeKnoten.class)) {
						g.drawString(String.valueOf(((AusgabeKnoten)kN.getType()).getDeltaK()), oneKnoX+staKnoX*i, oneKnoY+25+staKnoY*counter);
						g.setColor(Color.RED);
						g.drawString(String.valueOf(((AusgabeKnoten)kN.getType()).getYk()), oneKnoX+staKnoX*i, oneKnoY+75+staKnoY*counter);
					}
					g.setColor(Color.BLACK);
					counter++;
				}
			} else {
				kk++;
				for (KanteTyp vN:((KantenSchicht)netz.get(i)).getKanten()) {
					int multipL = 0;
					int multipR = 0;
					for (SchichtKomponent kN:((Schicht)netz.get(i-1)).getType().getKnoten()) {
						if(vN.getKnotenIDF() == kN.getId()) {
							for (SchichtKomponent kN2:((Schicht)netz.get(i+1)).getType().getKnoten()) {
								if(vN.getKnotenIDB() == kN2.getId()) {
									int val = 0;
									if(i == 3) {
										val = oneKnoX+LineLXAdd+staKnoX*((int)(i/6)+kk);
									} else if(i == 5) {
											val = oneKnoX+LineLXAdd+staKnoX*((int)(i/3)+kk);
									} else if(i != 1) {
										val = oneKnoX+LineLXAdd+staKnoX*((int)(i/3)+kk);
									} else {
										val = oneKnoX+LineLXAdd;
									}
									g.drawLine(val, oneKnoY+staKnoY*multipL+LineLYAdd, oneKnoX+staKnoX*(i+1), oneKnoY+staKnoY*multipR+1+LineRYAdd);
									g.setColor(Color.GREEN);
									g.drawString(String.valueOf(vN.getGewicht()),(val + (val + oneKnoX+staKnoX*(i+1))/2)/2, (oneKnoY+staKnoY*multipL+LineLYAdd +(oneKnoY+staKnoY*multipL+LineLYAdd + oneKnoY+staKnoY*multipR+1+LineRYAdd)/2)/2);
									g.setColor(Color.BLACK);
								} else {
									multipR++;
								}
							}
						} else {
							multipL++;
						}
					}
					
				}
			}
		}
	}
}
