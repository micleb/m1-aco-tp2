package fr.istic.m1.aco.miniediteur.v1.memento;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v1.command.Command;
import fr.istic.m1.aco.miniediteur.v1.command.Inserer;
import fr.istic.m1.aco.miniediteur.v1.invoker.StubController;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class MementoInsererTest {

private Moteur m;
private StubController ctrl;
	
	public MementoInsererTest() {
		StringBuffer b = new StringBuffer("123456789ABCDEF");
		this.m = new MoteurImpl(b);	
	}
	
	@Test
	public void testRestore() {
		Selection s = new SelectionImpl(1, 4); 
		m.selectionner(s);
		ctrl = new StubController("123456789ABCDEF", s, "2345");
		Command ins = new Inserer(m, ctrl);
		ins.executer();
		assertEquals("2345", m.getPresspapierContent());
		assertEquals("16789ABCDEF", m.getContent());
		
		ins.getMemento().restore();
		assertEquals("123456789ABCDEF", m.getContent());
	}
}