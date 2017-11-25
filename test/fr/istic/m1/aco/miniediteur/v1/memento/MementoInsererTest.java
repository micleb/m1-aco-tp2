package fr.istic.m1.aco.miniediteur.v1.memento;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v1.command.Command;
import fr.istic.m1.aco.miniediteur.v1.command.Inserer;
import fr.istic.m1.aco.miniediteur.v1.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class MementoInsererTest {

private Moteur m;
private static final String INITIAL_CONTENT = "123456789ABCDEF"; 

	public MementoInsererTest() {
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
	}
	
	@Test
	public void testRestoreSelectionNonVide() {
		Selection s = new SelectionImpl(1, 4); 
	
		Selectionner sel = new Selectionner(m,s);
		sel.executer();
		assertEquals("2345", m.getSelectedContent());
		
		Command ins = new Inserer(m, "****");
		ins.executer();
		assertEquals("1****6789ABCDEF", m.getContent());
	
		ins.getMemento().restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testRestoreSelectionVide() {
		Selection s = new SelectionImpl(1, 0); 
	
		Selectionner sel = new Selectionner(m,s);
		sel.executer();
		assertEquals("", m.getSelectedContent());
		
		Command ins = new Inserer(m, "****");
		ins.executer();
		assertEquals("1****23456789ABCDEF", m.getContent());
	
		ins.getMemento().restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
}