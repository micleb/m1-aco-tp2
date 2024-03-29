package fr.istic.m1.aco.miniediteur.v3.memento;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Inserer;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v3.invoker.StubIHM;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;

public class MementoInsererTest {

private Moteur m;
private StubIHM ui;
private Command selectionner; 
private Command inserer; 
private static final String INITIAL_CONTENT = "123456789ABCDEF"; 

	public MementoInsererTest() {
		ui = new StubIHM();
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);
		
		selectionner = new Selectionner(m, ui);
		inserer = new Inserer(m, ui);
	}
	
	@Test
	public void testRestoreSelectionNonVide() {
		ui.addFakeUserResponse(1);
		ui.addFakeUserResponse(4);
		selectionner.executer();
		assertEquals("2345", m.getSelectedContent());
		
		ui.addFakeUserResponse("****");
		inserer.executer();
		assertEquals("1****6789ABCDEF", m.getContent());
	
		Memento mem = inserer.getMemento();
		mem.restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
		mem.cancelRestore();
		assertEquals("1****6789ABCDEF", m.getContent());
	}
	
	@Test
	public void testRestoreSelectionVide() {
		ui.addFakeUserResponse(1);
		ui.addFakeUserResponse(0);
		selectionner.executer();
		assertEquals("", m.getSelectedContent());
		
		ui.addFakeUserResponse("****");
		inserer.executer();
		assertEquals("1****23456789ABCDEF", m.getContent());
	
		Memento mem = inserer.getMemento();
		mem.restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
		mem.cancelRestore();
		assertEquals("1****23456789ABCDEF", m.getContent());
	}
}