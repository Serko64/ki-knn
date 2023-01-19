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
	@Override
	public void repaint() {
		super.repaint();
	}
	private int oneKnoX = 80;
	private int oneKnoY = 40;
	private int staKnoX = 120;
	private int staKnoY = 200;
	private final int KnoW = 40;
	private final int KnoH = 40;
	private final int LineLXAdd = 40;
	private final int LineLYAdd = 20;
	private final int LineRYAdd = 20;
	private ArrayList<Double> valSaver;
	private ArrayList<Integer> resultSaver;
	private ArrayList<Object> netz;
	private ArrayList<Integer> right;
	private Netz n;
	private boolean roundOne;
	private static final long serialVersionUID = 2471902300638714690L;

	public Drawer(Netz netz) {
		super();
		this.netz = netz.getNetz();
		this.n = netz;
		this.valSaver = new ArrayList<>();
		this.resultSaver = new ArrayList<>();
		this.right = new ArrayList<>();
		this.roundOne = true;
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
				if(netz.size()-1 == i) {
//					System.out.println("hay " + valSaver);
//					if(!valSaver.contains(((AusgabeKnoten)((Schicht)netz.get(netz.size()-1)).getType().getKnoten().get(0).getType()).getOxVal())) {
//						valSaver.add(((AusgabeKnoten)((Schicht)netz.get(netz.size()-1)).getType().getKnoten().get(0).getType()).getOxVal());
//						resultSaver.add((int) ((AusgabeKnoten)((Schicht)netz.get(netz.size()-1)).getType().getKnoten().get(0).getType()).getYk());
//					}
					for(int counter1 = 0; counter1<right.size();counter1++) {
						if(right.get(counter1) == 1) {
							g.setColor(Color.GREEN);
							g.fillOval(oneKnoX+staKnoX*(i+1), oneKnoY+75+25*counter1, KnoH, KnoW);
							g.drawString(String.valueOf(resultSaver.get(counter1)), oneKnoX+50+staKnoX*(i+1), oneKnoY+100+25*counter1);
							g.drawString(String.valueOf(valSaver.get(counter1)), oneKnoX+75+staKnoX*(i+1), oneKnoY+100+25*counter1);
							g.setColor(Color.BLACK);
						} else {
							g.setColor(Color.RED);
							g.fillOval(oneKnoX+staKnoX*(i+1), oneKnoY+25*counter1, KnoH, KnoW);
							g.drawString(String.valueOf(resultSaver.get(counter1)), oneKnoX+50+staKnoX*(i+1), oneKnoY+100+25*counter1);
							g.drawString(String.valueOf(valSaver.get(counter1)), oneKnoX+75+staKnoX*(i+1), oneKnoY+100+25*counter1);
							g.setColor(Color.BLACK);
						} 
					}
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
	public void prozent() {
		int w1 = 0;
		int w2 = 0;
		w2 = right.size();
		System.out.println(w2);
		System.out.println(w1);
		for(int i : right){
			w1 = w1 + i;
		}
		double result = w1/w2;
		System.out.println(result);
	}
	public void resetall() {
		valSaver.clear();
	}
	public void setValSaver(ArrayList<Double> valData) {
		this.valSaver = valData;
	}
	public void setRight(ArrayList<Integer> right) {
		this.right = right;
	}
	public void setResultSaver(ArrayList<Integer> resultSaver) {
		this.resultSaver = resultSaver;
	}
	
	
}
