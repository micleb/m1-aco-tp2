package fr.istic.m1.aco.miniediteur.v1.memento;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v1.command.Command;
import fr.istic.m1.aco.miniediteur.v1.command.Couper;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class MementoCouperTest {
	Moteur m;
	
	public MementoCouperTest() {
		StringBuffer b = new StringBuffer("123456789ABCDEF");
		this.m = new MoteurImpl(b);	
	}
	
	@Test
	public void testRestore() {
		Selection s = new SelectionImpl(1, 4); 
		m.selectionner(s);
		Command cut = new Couper(m);
		cut.executer();
		assertEquals("2345", m.getPresspapierContent());
		assertEquals("16789ABCDEF", m.getContent());
		
		cut.getMemento().restore();
		assertEquals("123456789ABCDEF", m.getContent());
	}

}
