package fr.istic.m1.aco.miniediteur.v3.receiver;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Coller;
import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Copier;
import fr.istic.m1.aco.miniediteur.v3.command.Couper;
import fr.istic.m1.aco.miniediteur.v3.command.Inserer;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v3.invoker.StubIHM;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;
import fr.istic.m1.aco.miniediteur.v3.receiver.EnregistreurImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;

public class EnregistreurTest {
	private Moteur m;
	
	private StubIHM ui;
	private Command selectionner; 
	private Command couper;
	private Command coller;
	private Command copier;
	private Command inserer;
	
	private static final String INITIAL_CONTENT = "123456789ABCDEF";
	private Enregistreur rec;
	
	@Before
	public void setUp() {
		ui = new StubIHM();
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
		this.rec = new EnregistreurImpl();
		
		couper = new Couper(m);
		coller = new Coller(m);
		copier = new Copier(m);
		selectionner = new Selectionner(m, ui);
		inserer = new Inserer(m, ui);
	}
	
	private void makeSelection(int start, int size) {
		ui.addFakeUserResponse(start);
		ui.addFakeUserResponse(size);
		selectionner.executer();
		rec.rajouter(selectionner);
	}
	
	@Test
	public void testRestore() {
		rec.demarrer();
		
		makeSelection(1, 4);
		Command mv = new Couper(m);
		mv.executer();
		rec.rajouter(mv);
		assertEquals("16789ABCDEF",m.getContent());
		assertEquals("2345",  m.getPresspapierContent());
		
		makeSelection(5, 0);
		Command paste = new Coller(m);
		paste.executer();
		rec.rajouter(paste);
		assertEquals("167892345ABCDEF", m.getContent());
		
		rec.stopper();
		rec.rejouer();
		assertEquals("123456789ABCDEF", m.getContent());
	}
}