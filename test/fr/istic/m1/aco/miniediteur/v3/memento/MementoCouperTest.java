package fr.istic.m1.aco.miniediteur.v3.memento;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Coller;
import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Copier;
import fr.istic.m1.aco.miniediteur.v3.command.Couper;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v3.invoker.StubIHM;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

public class MementoCouperTest {
	private Moteur m;
	private StubIHM ui;
	private Command selectionner; 
	private Command couper;

	
	public MementoCouperTest() {
		ui = new StubIHM();
		StringBuffer b = new StringBuffer("123456789ABCDEF");
		this.m = new MoteurImpl(b);	
		
		couper = new Couper(m);
		selectionner = new Selectionner(m, ui);
	}
	
	@Test
	public void testRestore() {		
		ui.addFakeUserResponse(1);
		ui.addFakeUserResponse(4);
		selectionner.executer();
		couper.executer();
		assertEquals("2345", m.getPresspapierContent());
		assertEquals("16789ABCDEF", m.getContent());
		
		Memento mem = couper.getMemento();
		mem.restore();
		assertEquals("123456789ABCDEF", m.getContent());
		mem.cancelRestore();
		assertEquals("16789ABCDEF", m.getContent());
	}
	
	@Test
	public void testRestore2() {	
		ui.addFakeUserResponse(0);
		ui.addFakeUserResponse(15);
		selectionner.executer();
		couper.executer();
		assertEquals("123456789ABCDEF", m.getPresspapierContent());
		assertEquals("", m.getContent());
		
		Memento mem = couper.getMemento();
		mem.restore();
		assertEquals("123456789ABCDEF", m.getContent());
		
		mem.cancelRestore();
		assertEquals("", m.getContent());
	}
}
