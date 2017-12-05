package fr.istic.m1.aco.miniediteur.v3.memento;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Coller;
import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Copier;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v3.invoker.StubIHM;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;


public class MementoCollerTest {

	Moteur m;
	private StubIHM ui;
	private Command selectionner; 
	private Command coller;
	private Command copier;
	private final String INITIAL_CONTENT = "123456789ABCDEF";
	
	public MementoCollerTest() {
		ui = new StubIHM();

		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
		
		coller = new Coller(m);
		copier = new Copier(m);
		selectionner = new Selectionner(m, ui);
	}
	
	@Test
	public void testRestoreSelectionVide() {
		ui.addFakeUserResponse(1);
		ui.addFakeUserResponse(4);
		selectionner.executer();
		copier.executer();
		assertEquals("2345", m.getPresspapierContent());
	
		ui.addFakeUserResponse(9);
		ui.addFakeUserResponse(0);
		selectionner.executer();
		coller.executer();
		assertEquals("1234567892345ABCDEF", m.getContent());
		
		Memento mem = coller.getMemento();
		mem.restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
		
		mem.cancelRestore();
		assertEquals("1234567892345ABCDEF", m.getContent());
	}
	
	@Test
	public void testRestoreSelectionNonVide() {
		ui.addFakeUserResponse(1);
		ui.addFakeUserResponse(4);
		selectionner.executer();
		copier.executer();
		assertEquals("2345", m.getPresspapierContent());
	
		ui.addFakeUserResponse(9);
		ui.addFakeUserResponse(6);
		selectionner.executer();
		coller.executer();

		assertEquals("1234567892345", m.getContent());
		
		Memento mem = coller.getMemento();
		mem.restore();
		assertEquals(INITIAL_CONTENT, m.getContent());
		
		mem.cancelRestore();
		assertEquals("1234567892345", m.getContent());
	}
}
