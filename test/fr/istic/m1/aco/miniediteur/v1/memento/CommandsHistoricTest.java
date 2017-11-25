package fr.istic.m1.aco.miniediteur.v1.memento;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v1.command.Coller;
import fr.istic.m1.aco.miniediteur.v1.command.Command;
import fr.istic.m1.aco.miniediteur.v1.command.Copier;
import fr.istic.m1.aco.miniediteur.v1.command.Couper;
import fr.istic.m1.aco.miniediteur.v1.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v1.invoker.StubController;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class CommandsHistoricTest {
	private Moteur m;
	private CommandsHistoric cmds = new CommandsHistoric();
	private static final String INITIAL_CONTENT = "123456789ABCDEF";
	
	@Before
	public void setUp() {
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
	}
	
	@Test
	public void testCouperSimple() {
		Selection s = new SelectionImpl(1, 4); 
		Command sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		
		Command cut = new Couper(m);
		cut.executer();
		cmds.registerCommand(cut);
		//cut.executer();
		assertEquals("16789ABCDEF", m.getContent());
		assertEquals("2345", m.getPresspapierContent());
		
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
		assertEquals("2345", m.getPresspapierContent());
		System.out.println(m.getSelectedContent());
		cmds.redo();
		assertEquals("16789ABCDEF", m.getContent());
	}
	
	@Test
	public void testCollerSimple() {
		Selection s = new SelectionImpl(1, 4); 
		Command sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		new Copier(m).executer();
		assertEquals("2345", m.getPresspapierContent());
		s = new SelectionImpl(9, 0); 
		createSelectCmd(s).executer();
		Command paste = new Coller(m);
		paste.executer();
		assertEquals("1234567892345ABCDEF", m.getContent());
		cmds.registerCommand(paste);
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
		
		//
		cmds.redo();
		assertEquals("1234567892345ABCDEF", m.getContent());
	}
	
	@Test
	public void testCollerMultiple() {
		Selection s = new SelectionImpl(1, 4); 
		Command select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		new Copier(m).executer();
		assertEquals("2345", m.getPresspapierContent());
		
		//ETAPE 1 : A partir de l'indice 9, on fait une selection de taille 0, on colle donc "2345" à la suite de "9" sans suppression.
		final String stage1 = "1234567892345ABCDEF";
		s = new SelectionImpl(9, 0); 
		Command sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals(stage1, m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 2 : A partir de l'indice 0, on fait une selection de taille 1, on colle donc "2345" en supprimant "1"
		final String stage2 = "2345234567892345ABCDEF"; 
		s = new SelectionImpl(0, 1);
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
				
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage2, m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 3 :A partir de l'indice 20, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		final String stage3 = "2345234567892345ABCD2345EF";
		s = new SelectionImpl(20, 0); 
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage3, m.getContent());
		cmds.registerCommand(paste);
		
		
		//On annule l'étape 3, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 2.
		cmds.undo();
		assertEquals(stage2, m.getContent());
		//On annule l'étape 2, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 1.
		cmds.undo();
		assertEquals(stage1, m.getContent());
		//On annule l'étape 1, on s'attend donc à retrouver le contenu initial.
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
		
		
		//Après avoir restoré le contenu initial, on refait l'étape 1 (equivalent de CTRL+Y),
		//donc on doit se retrouver avec le même contenu qu'a la fin de l'étape 1. 
		cmds.redo();
		assertEquals(stage1, m.getContent());
		//Restoration de registerCommand(cmd);	la commande suivante, donc on retrouve le conteu de l'étape 2.
		cmds.redo();
		assertEquals(stage2, m.getContent());
		//Puisqu'on a fait 3 redo après nos 3 undo, c'est comme si on avait jamais fait d'undo.
		//Donc on retrouve à la situation de l'étape 3.
		cmds.redo();
		assertEquals(stage3, m.getContent());	
		
		//Quelques CTRL-Z, CTRL-Y suivant la même logique.
		cmds.undo();
		assertEquals(stage2, m.getContent());
		cmds.undo();
		assertEquals(stage1, m.getContent());
		cmds.redo();
		assertEquals(stage2, m.getContent());
		cmds.undo();
		assertEquals(stage1, m.getContent());
		cmds.redo();
		assertEquals(stage2, m.getContent());
		cmds.redo();
		assertEquals(stage3, m.getContent());
	}
	
	@Test
	public void testCouperMultiple() {
		Selection s = new SelectionImpl(1, 4); 
		Command select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		
		//ETAPE 1 : On execute un couper des caractères 1 à 4.
		final String stage1 = "16789ABCDEF";
		Command mv = new Couper(m);
		//mv.executer();
		cmds.registerCommand(mv);
		mv.executer();
		assertEquals("2345", m.getPresspapierContent());
		assertEquals("16789ABCDEF", m.getContent());
		
		//ETAPE 2 : A partir de l'indice 9, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		final String stage2 = "16789ABCD2345EF";
		s = new SelectionImpl(9, 0); 
		Command sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals(stage2, m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 3 : A partir de l'indice 0, on fait une selection de taille 1, on colle donc "2345" en supprimant "1"
		final String stage3 = "23456789ABCD2345EF"; 
		s = new SelectionImpl(0, 1);
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
				
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage3, m.getContent());
		cmds.registerCommand(paste);
				
		//ETAPE 4 :A partir de l'indice 20, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		final String stage4 = "23456789ABCD2345EF2345";
		s = new SelectionImpl(18, 0); 
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage4, m.getContent());
		cmds.registerCommand(paste);
		
		
		//On annule l'étape 4, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 3.
		cmds.undo();
		assertEquals(stage3, m.getContent());
		//On annule l'étape 3, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 2.
		cmds.undo();
		assertEquals(stage2, m.getContent());
		//On annule l'étape 2, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 1.
		cmds.undo();
		assertEquals(stage1, m.getContent());
		//On annule l'étape 1, on s'attend donc à retrouver le contenu initial.
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
		
		//Après avoir restoré le contenu initial, on refait l'étape 1 (equivalent de CTRL+Y),
		//donc on doit se retrouver avec le même contenu qu'a la fin de l'étape 1. 
		cmds.redo();
		assertEquals(stage1, m.getContent());
		//Restoration de la commande suivante, donc on retrouve le conteu de l'étape 2.
		cmds.redo();
		assertEquals(stage2, m.getContent());
		//Puisqu'on a fait 3 redo après nos 3 undo, c'est comme si on avait jamais fait d'undo.
		//Donc on retrouve à la situation de l'étape 3.
		cmds.redo();
		assertEquals(stage3, m.getContent());
		
		cmds.redo();
		assertEquals(stage4, m.getContent());
		
	}
	
	private Command createSelectCmd(Selection s) {
		return new Selectionner(m, s);
	}
	
	
	
	
	


}
