package fr.istic.m1.aco.miniediteur.v1.memento;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v1.command.Coller;
import fr.istic.m1.aco.miniediteur.v1.command.Command;
import fr.istic.m1.aco.miniediteur.v1.command.Couper;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class CommandsHistoricTest {
	Moteur m;
	CommandsHistoric cmds = new CommandsHistoric();

	@Before
	public void setUp() {
		StringBuffer b = new StringBuffer("123456789ABCDEF");
		this.m = new MoteurImpl(b);	
	}
	
	@Test
	public void testCouperSimple() {
		Selection s = new SelectionImpl(1, 4); 
		m.selectionner(s);
		Command cut = new Couper(m);
		cut.executer();
		assertEquals("16789ABCDEF", m.getContent());
		cmds.registerCommand(cut);
		cmds.undo();
		assertEquals("123456789ABCDEF", m.getContent());
	}
	
	@Test
	public void testCollerSimple() {
		Selection s = new SelectionImpl(1, 4); 
		m.selectionner(s);
		m.copier();
		assertEquals("2345", m.getPresspapierContent());
		s = new SelectionImpl(9, 0); 
		m.selectionner(s);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals("1234567892345ABCDEF", m.getContent());
		cmds.registerCommand(paste);
		cmds.undo();
		assertEquals("123456789ABCDEF", m.getContent());
		
		//
		cmds.redo();
		assertEquals("1234567892345ABCDEF", m.getContent());
	}
	
	
	@Test
	public void testCollerMultiple() {
		Selection s = new SelectionImpl(1, 4); 
		m.selectionner(s);
		m.copier();
		assertEquals("2345", m.getPresspapierContent());
		
		//ETAPE 1 : A partir de l'indice 9, on fait une selection de taille 0, on colle donc "2345" à la suite de "9" sans suppression.
		s = new SelectionImpl(9, 0); 
		m.selectionner(s);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals("1234567892345ABCDEF", m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 2 : A partir de l'indice 0, on fait une selection de taille 1, on colle donc "2345" en supprimant "1"
		s = new SelectionImpl(0, 1);
		m.selectionner(s);
		paste = new Coller(m);
		paste.executer();
		assertEquals("2345234567892345ABCDEF", m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 3 :A partir de l'indice 20, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		s = new SelectionImpl(20, 0); 
		m.selectionner(s);
		paste = new Coller(m);
		paste.executer();
		assertEquals("2345234567892345ABCD2345EF", m.getContent());
		cmds.registerCommand(paste);
		
		
		//On annule l'étape 3, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 2.
		cmds.undo();
		assertEquals("2345234567892345ABCDEF", m.getContent());
		//On annule l'étape 2, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 1.
		cmds.undo();
		assertEquals("1234567892345ABCDEF", m.getContent());
		//On annule l'étape 1, on s'attend donc à retrouver le contenu initial.
		cmds.undo();
		assertEquals("123456789ABCDEF", m.getContent());
	}
	
	
	
	
	
	


}
