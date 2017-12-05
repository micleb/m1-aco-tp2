package fr.istic.m1.aco.miniediteur.v3.memento;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Delete;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v3.invoker.StubIHM;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;

public class MementoSupprimerTest {

	private Moteur m;
	private StubIHM ui;
	private Command selectionner; 
	private Command supprimer;
	private static final String INITIAL_CONTENT = "123456789ABCDEF"; 
	
	public MementoSupprimerTest() {
		ui = new StubIHM();
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);
		
		selectionner = new Selectionner(m, ui);
		supprimer = new Delete(m);
	}
	
	@Test
	public void testRestoreSelectionNonVide() {
		ui.addFakeUserResponse(9);
		ui.addFakeUserResponse(3);
		selectionner.executer();
		assertEquals("ABC", m.getSelectedContent());
		
		supprimer.executer();
		assertEquals("123456789DEF", m.getContent());
	
		Memento mem = supprimer.getMemento();
		mem.restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
		mem.cancelRestore();
		assertEquals("123456789DEF", m.getContent());
	}
	
	@Test
	public void testRestoreSelectionVide() {
		ui.addFakeUserResponse(8);
		ui.addFakeUserResponse(0);
		selectionner.executer();
		assertEquals("", m.getSelectedContent());
		
		supprimer.executer();
		assertEquals(INITIAL_CONTENT, m.getContent());
	
		Memento mem = supprimer.getMemento();
		mem.restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
		mem.cancelRestore();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
}
