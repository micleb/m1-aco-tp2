package fr.istic.m1.aco.miniediteur.v1.memento;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v1.command.Coller;
import fr.istic.m1.aco.miniediteur.v1.command.Command;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class MementoCollerTest {

	Moteur m;
	private final String INITIAL_CONTENT = "123456789ABCDEF";
	
	public MementoCollerTest() {
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
	}
	
	@Test
	public void testRestoreSelectionVide() {
		Selection s = new SelectionImpl(1, 4); 
		m.selectionner(s);
		m.copier();
		assertEquals("2345", m.getPresspapierContent());
	
		s = new SelectionImpl(9, 0); 
		m.selectionner(s);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals("1234567892345ABCDEF", m.getContent());
		
		paste.getMemento().restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testRestoreSelectionNonVide() {
		Selection s = new SelectionImpl(1, 4); 
		m.selectionner(s);
		m.copier();
		assertEquals("2345", m.getPresspapierContent());
	
		s = new SelectionImpl(9, 6); 
		m.selectionner(s);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals("1234567892345", m.getContent());
		
		paste.getMemento().restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	

}
