package hft.stuttgart.ki.vis;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

import hft.stuttgart.ki.main.Netz;
import hft.stuttgart.ki.parts.Schicht.KantenSchicht;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtTyp;

public class Drawer extends JComponent {
	private int oneKnoX = 80;
	private int oneKnoY = 40;
	private int staKnoX = 80;
	private int staKnoY = 80;
	private final int KnoW = 40;
	private final int KnoH = 40;
	private final int LineLXAdd = 40;
	private final int LineLYAdd = 20;
	private final int LineRXAdd = 0;
	private final int LineRYAdd = 20;
	private ArrayList<Object> netz;
	private static final long serialVersionUID = 2471902300638714690L;

	public Drawer(ArrayList<Object> netz) {
		super();
		this.netz = netz;
	}
	public void paint(Graphics g)
    {
		drawNetz(this.netz, g);
        // draw and display the line
//        g.drawLine(60, 30, 100, 30);
//        g.setColor(Color.BLUE);
//        g.fillOval(40, 20, 20, 20);
//        g.fillOval(100, 80, 20, 20);
    }
	public void drawNetz(ArrayList<Object> netz, Graphics g) {
		System.out.println("Netz " + netz.size());
		int kk = 0;
		for(int i=0;i<netz.size();i++) {
			if(i%2 == 0) {
				int counter = 0;
				for (SchichtKomponent kN:((Schicht)netz.get(i)).getType().getKnoten()) {
					System.out.println("Knoten " + i + " " + ((Schicht)netz.get(i)).getType().getKnoten().size());
					if(((KnotenTyp)kN.getType()).isBias()) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLACK);
					}
					g.fillOval(oneKnoX+staKnoX*i, oneKnoY+staKnoY*counter, KnoH, KnoW);
					counter++;
				}
			} else {
				kk++;
				for (KanteTyp vN:((KantenSchicht)netz.get(i)).getKanten()) {
					int multipL = 0;
					int multipR = 0;
					for (SchichtKomponent kN:((Schicht)netz.get(i-1)).getType().getKnoten()) {
						if(vN.getKnotenIDF() == kN.getId()) {
							g.setColor(Color.GREEN);
							g.drawString(String.valueOf(i), oneKnoX+staKnoX*(i-1), oneKnoY+staKnoY*multipL);
							g.setColor(Color.BLACK);
							for (SchichtKomponent kN2:((Schicht)netz.get(i+1)).getType().getKnoten()) {
								if(vN.getKnotenIDB() == kN2.getId()) {
									int val = 0;
									if(i == 3) {
										System.out.println("kk" + kk);
										val = oneKnoX+LineLXAdd+staKnoX*((int)(i/6)+kk);
									} else if(i == 5) {
											System.out.println("kk" + kk);
											val = oneKnoX+LineLXAdd+staKnoX*((int)(i/3)+kk);
									} else if(i != 1) {
										val = oneKnoX+LineLXAdd+staKnoX*((int)(i/3)+kk);
									} else {
										val = oneKnoX+LineLXAdd;
									}
									g.drawLine(val, oneKnoY+staKnoY*multipL+LineLYAdd, oneKnoX+staKnoX*(i+1), oneKnoY+staKnoY*multipR+1+LineRYAdd);
									g.setColor(Color.GREEN);
									g.drawString(String.valueOf(vN.getGewicht()).substring(0, 3),(val + (val + oneKnoX+staKnoX*(i+1))/2)/2, (oneKnoY+staKnoY*multipL+LineLYAdd +(oneKnoY+staKnoY*multipL+LineLYAdd + oneKnoY+staKnoY*multipR+1+LineRYAdd)/2)/2);
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