package fr.istic.m1.aco.miniediteur.v3.receiver;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Coller;
import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Copier;
import fr.istic.m1.aco.miniediteur.v3.command.Couper;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;

public class EnregistreurTest {
	private Moteur m;
	private static final String INITIAL_CONTENT = "123456789ABCDEF";
	private Enregistreur rec;
	
	@Before
	public void setUp() {
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
		this.rec = new EnregistreurImpl();
	}
	
	@Test
	public void testRestore() {
		rec.demarrer();
		
		Selection s = new SelectionImpl(1, 4); 
		Command select = new Selectionner(m, s);
		select.executer();
		rec.rajouter(select);
		Command mv = new Couper(m);
		mv.executer();
		rec.rajouter(mv);
		assertEquals("16789ABCDEF",m.getContent());
		assertEquals("2345",  m.getPresspapierContent());
		
		
		s = new SelectionImpl(5, 0); 
		select = new Selectionner(m, s);
		select.executer();
		rec.rajouter(select);
		Command paste = new Coller(m);
		paste.executer();
		rec.rajouter(paste);
		assertEquals("167892345ABCDEF", m.getContent());
		
		rec.stoper();
		rec.rejouer();
		assertEquals("123456789ABCDEF", m.getContent());
	}
}
