package hft.stuttgart.ki.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.EventQueue;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.function.Executable;

import hft.stuttgart.ki.exceptions.WrongUsedTypeclassException;
import hft.stuttgart.ki.main.Netz;
import hft.stuttgart.ki.parts.Schicht.KantenSchicht;
import hft.stuttgart.ki.parts.Schicht.Schicht;
import hft.stuttgart.ki.parts.SchichtKomponents.KanteTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.KnotenTyp;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtKomponent;
import hft.stuttgart.ki.parts.SchichtKomponents.SchichtTyp;
import hft.stuttgart.ki.vis.showNN;
@TestInstance(Lifecycle.PER_CLASS)
class TestGenerationOfDiagram {
	private Netz netz;
	private int eZ;
	private int[] hZ;
	private int aZ;
	@BeforeEach
	void generateKnoten() {
		Netz netz = new Netz();
		this.eZ = 3;
		this.hZ = new int[] {3};
		this.aZ = 1;
		try {
			netz.createKnoten(eZ,hZ,aZ);
		} catch (WrongUsedTypeclassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		netz.setAlpha(0.2);
		this.netz = netz;
	}
	@Test
	void testEingabeSchicht() {
		ArrayList<SchichtKomponent> knoten = ((Schicht)netz.getNetz().get(0)).getType().getKnoten();
		assertTrue(((KnotenTyp)knoten.get(0).getType()).isBias());
		assertTrue( knoten.size() == eZ);
		assertTrue(!((KnotenTyp)knoten.get(1).getType()).isBias());
		assertTrue(!((KnotenTyp)knoten.get(2).getType()).isBias());
	}
	@Test
	void testHiddenSchicht() {
		ArrayList<SchichtKomponent> knoten = ((Schicht)netz.getNetz().get(1)).getType().getKnoten();
		assertTrue(((KnotenTyp)knoten.get(0).getType()).isBias());
		assertTrue( knoten.size() == hZ[0]);
		assertTrue(!((KnotenTyp)knoten.get(1).getType()).isBias());
		assertTrue(!((KnotenTyp)knoten.get(2).getType()).isBias());
	}
	@Test
	void testAusgabeSchicht() {
		ArrayList<SchichtKomponent> knoten = ((Schicht)netz.getNetz().get(2)).getType().getKnoten();
		assertTrue(!((KnotenTyp)knoten.get(0).getType()).isBias());
		assertTrue( knoten.size() == aZ);
	}
	@Test
	void testKantenVerbundenEinHidd() {
		netz.addKanten();
		ArrayList<KanteTyp> kanten = ((KantenSchicht)netz.getNetz().get(1)).getKanten();
		ArrayList<SchichtKomponent> knoten1 = ((Schicht)netz.getNetz().get(0)).getType().getKnoten();
		ArrayList<SchichtKomponent> knoten2 = ((Schicht)netz.getNetz().get(2)).getType().getKnoten();
		assertTrue(kanten.size() == 6);
		int counter = 0;
		for (SchichtKomponent sK1 : knoten1) {
			if(((KnotenTyp)sK1.getType()).isBias()) {
				System.out.println(((KantenSchicht)netz.getNetz().get(1)).findConnectedFrontKnotenId(sK1.getId()).size());
				assertTrue(((KantenSchicht)netz.getNetz().get(1)).findConnectedFrontKnotenId(sK1.getId()).size() == 2);
			}
			for (SchichtKomponent sK2 : knoten2) {
			}
		}
	}
	@Test
	void testForward() {
		netz.addKanten();
		ArrayList<SchichtKomponent> knoten1 = ((Schicht)netz.getNetz().get(0)).getType().getKnoten();
		((KnotenTyp)knoten1.get(0).getType()).setOxVal(1);
		((KnotenTyp)knoten1.get(1).getType()).setOxVal(0.1);
		((KnotenTyp)knoten1.get(2).getType()).setOxVal(0.3);
		netz.doForwardCalculation();
		ArrayList<SchichtKomponent> knoten2 = ((Schicht)netz.getNetz().get(2)).getType().getKnoten();
		System.out.println("Vlll" + ((KnotenTyp)knoten2.get(1).getType()).getOxVal());
		assertTrue(((KnotenTyp)knoten2.get(1).getType()).getOxVal() == 0.542);
	}
}
