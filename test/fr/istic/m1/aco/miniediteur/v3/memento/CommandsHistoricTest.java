package fr.istic.m1.aco.miniediteur.v3.memento;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Coller;
import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Copier;
import fr.istic.m1.aco.miniediteur.v3.command.Couper;
import fr.istic.m1.aco.miniediteur.v3.command.Inserer;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoricImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

public class CommandsHistoricTest {
	private Moteur m;
	private CommandsHistoric cmds = new CommandsHistoricImpl();
	private static final String INITIAL_CONTENT = "123456789ABCDEF";
	
	@Before
	public void setUp() {
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
	}
	
	@Test
	public void testUndoCouperSimple() {
		Selection s = new SelectionImpl(1, 4); 
		Command sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		
		Command cut = new Couper(m);
		cut.executer();
		cmds.registerCommand(cut);
		assertEquals("16789ABCDEF", m.getContent());
		assertEquals("2345", m.getPresspapierContent());
		
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
		assertEquals("2345", m.getPresspapierContent());
	}
	
	@Test
	public void testRedoCouperSimple() {
		testUndoCouperSimple();
		cmds.redo();
		assertEquals("16789ABCDEF", m.getContent());
	}
	
	@Test
	public void testUndoCollerSimple() {
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
	}
	
	@Test
	public void testRedoCollerSimple() {
		testUndoCollerSimple();
		cmds.redo();
		assertEquals("1234567892345ABCDEF", m.getContent());
	}
	
	final String stage1CollerMultiple = "1234567892345ABCDEF";
	final String stage2CollerMultiple = "2345234567892345ABCDEF"; 
	final String stage3CollerMultiple = "2345234567892345ABCD2345EF";
	@Test
	public void testCollerMultipleUndo() {
		Selection s = new SelectionImpl(1, 4); 
		Command select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		new Copier(m).executer();
		assertEquals("2345", m.getPresspapierContent());
		
		//ETAPE 1 : A partir de l'indice 9, on fait une selection de taille 0, on colle donc "2345" à la suite de "9" sans suppression.
		s = new SelectionImpl(9, 0); 
		Command sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals(stage1CollerMultiple, m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 2 : A partir de l'indice 0, on fait une selection de taille 1, on colle donc "2345" en supprimant "1"
		s = new SelectionImpl(0, 1);
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
				
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage2CollerMultiple, m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 3 :A partir de l'indice 20, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		s = new SelectionImpl(20, 0); 
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage3CollerMultiple, m.getContent());
		cmds.registerCommand(paste);
		
		
		//On annule l'étape 3, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 2.
		cmds.undo();
		assertEquals(stage2CollerMultiple, m.getContent());
		//On annule l'étape 2, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 1.
		cmds.undo();
		assertEquals(stage1CollerMultiple, m.getContent());
		//On annule l'étape 1, on s'attend donc à retrouver le contenu initial.
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testCollerMultipleRedo() {
		testCollerMultipleUndo();
		//Après avoir restoré le contenu initial, on refait l'étape 1 (equivalent de CTRL+Y),
		//donc on doit se retrouver avec le même contenu qu'a la fin de l'étape 1. 
		cmds.redo();
		assertEquals(stage1CollerMultiple, m.getContent());
		//Restoration de registerCommand(cmd);	la commande suivante, donc on retrouve le conteu de l'étape 2.
		cmds.redo();
		assertEquals(stage2CollerMultiple, m.getContent());
		//Puisqu'on a fait 3 redo après nos 3 undo, c'est comme si on avait jamais fait d'undo.
		//Donc on retrouve à la situation de l'étape 3.
		cmds.redo();
		assertEquals(stage3CollerMultiple, m.getContent());	
	}
	
	@Test 
	public void testCollerMultipleUndoRedo() {
		testCollerMultipleRedo();
		//Quelques CTRL-Z, CTRL-Y suivant la même logique.
		cmds.undo();
		assertEquals(stage2CollerMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1CollerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2CollerMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1CollerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2CollerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage3CollerMultiple, m.getContent());
	}
	
	final String stage1CouperMultiple = "16789ABCDEF";
	final String stage2CouperMultiple = "16789ABCD2345EF";
	final String stage3CouperMultiple = "23456789ABCD2345EF"; 
	final String stage4CouperMultiple = "23456789ABCD2345EF2345";
	
	@Test
	public void testCouperMultipleUndo() {
		Selection s = new SelectionImpl(1, 4); 
		Command select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		
		//ETAPE 1 : On execute un couper des caractères 1 à 4.
		Command mv = new Couper(m);
		//mv.executer();
		cmds.registerCommand(mv);
		mv.executer();
		assertEquals("2345", m.getPresspapierContent());
		assertEquals("16789ABCDEF", m.getContent());
		
		//ETAPE 2 : A partir de l'indice 9, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		s = new SelectionImpl(9, 0); 
		Command sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		Command paste = new Coller(m);
		paste.executer();
		assertEquals(stage2CouperMultiple, m.getContent());
		cmds.registerCommand(paste);
		
		//ETAPE 3 : A partir de l'indice 0, on fait une selection de taille 1, on colle donc "2345" en supprimant "1".
		s = new SelectionImpl(0, 1);
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
				
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage3CouperMultiple, m.getContent());
		cmds.registerCommand(paste);
				
		//ETAPE 4 :A partir de l'indice 20, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		s = new SelectionImpl(18, 0); 
		sel = createSelectCmd(s);
		sel.executer();
		cmds.registerCommand(sel);
		paste = new Coller(m);
		paste.executer();
		assertEquals(stage4CouperMultiple, m.getContent());
		cmds.registerCommand(paste);
		
		
		//On annule l'étape 4, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 3.
		cmds.undo();
		assertEquals(stage3CouperMultiple, m.getContent());
		//On annule l'étape 3, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 2.
		cmds.undo();
		assertEquals(stage2CouperMultiple, m.getContent());
		//On annule l'étape 2, on s'attend donc à retrouver le même contenu qu'a la fin de l'étape 1.
		cmds.undo();
		assertEquals(stage1CouperMultiple, m.getContent());
		//On annule l'étape 1, on s'attend donc à retrouver le contenu initial.
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testCouperMultipleRedo() {
		testCouperMultipleUndo();
		//Après avoir restoré le contenu initial, on refait l'étape 1 (equivalent de CTRL+Y),
		//donc on doit se retrouver avec le même contenu qu'a la fin de l'étape 1. 
		cmds.redo();
		assertEquals(stage1CouperMultiple, m.getContent());
		//Restoration de la commande suivante, donc on retrouve le conteu de l'étape 2.
		cmds.redo();
		assertEquals(stage2CouperMultiple, m.getContent());
		//Puisqu'on a fait 3 redo après nos 3 undo, c'est comme si on avait jamais fait d'undo.
		//Donc on retrouve à la situation de l'étape 3.
		cmds.redo();
		assertEquals(stage3CouperMultiple, m.getContent());
		
		cmds.redo();
		assertEquals(stage4CouperMultiple, m.getContent());
	}
	@Test
	public void testUndoInserer(){
		Selection s = new SelectionImpl(9, 3); 
		Command select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		
		Command inserer = new Inserer(m, "XYZT");
		inserer.executer();
		assertEquals("123456789XYZTDEF", m.getContent());
		cmds.registerCommand(inserer);
		cmds.undo();
		
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testRedoInserer() {
		testUndoInserer();
		cmds.redo();
		assertEquals("123456789XYZTDEF", m.getContent());
	}
	
	final String stage1InsererMultiple = "123456789XYZTDEF";
	final String stage2InsererMultiple = "12345!..+6789XYZTDEF";
	final String stage3InsererMultiple = "12345!..+6789XYZTDEF000";
	final String stage4InsererMultiple = "12345!..+6789XYZTDEF00*";
			
	@Test
	public void testUndoInsererMultiple(){
		Selection s = new SelectionImpl(9, 3); 
		Command select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		
		Command inserer = new Inserer(m, "XYZT");
		inserer.executer();
		assertEquals(stage1InsererMultiple, m.getContent());
		cmds.registerCommand(inserer);
		
		s = new SelectionImpl(5, 0); 
		select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		inserer = new Inserer(m, "!..+");
		cmds.registerCommand(inserer);
		inserer.executer();
		assertEquals(stage2InsererMultiple, m.getContent());
		
		s = new SelectionImpl(20, 0); 
		select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		inserer = new Inserer(m, "000");
		cmds.registerCommand(inserer);
		inserer.executer();
		assertEquals(stage3InsererMultiple, m.getContent());
		
		s = new SelectionImpl(22, 1); 
		select = this.createSelectCmd(s);
		select.executer();
		cmds.registerCommand(select);
		inserer = new Inserer(m, "*");
		cmds.registerCommand(inserer);
		inserer.executer();
		assertEquals(stage4InsererMultiple, m.getContent());
		
		cmds.undo();
		cmds.undo();
		cmds.undo();
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testInsererMultipleRedo() {
		testUndoInsererMultiple();
		//Après avoir restoré le contenu initial, on refait l'étape 1 (equivalent de CTRL+Y),
		//donc on doit se retrouver avec le même contenu qu'a la fin de l'étape 1. 
		cmds.redo();
		assertEquals(stage1InsererMultiple, m.getContent());
		//Restoration de la commande suivante, donc on retrouve le conteu de l'étape 2.
		cmds.redo();
		assertEquals(stage2InsererMultiple, m.getContent());
		//Puisqu'on a fait 3 redo après nos 3 undo, c'est comme si on avait jamais fait d'undo.
		//Donc on retrouve à la situation de l'étape 3.
		cmds.redo();
		assertEquals(stage3InsererMultiple, m.getContent());
		
		cmds.redo();
		assertEquals(stage4InsererMultiple, m.getContent());
	}
	
	@Test 
	public void testInsererMultipleUndoRedo() {
		testInsererMultipleRedo();
		//Quelques CTRL-Z, CTRL-Y suivant la même logique.
		cmds.undo();
		assertEquals(stage3InsererMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage4InsererMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage3InsererMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage2InsererMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1InsererMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2InsererMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1InsererMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2InsererMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage3InsererMultiple, m.getContent());
	}
	
	private Command createSelectCmd(Selection s) {
		return new Selectionner(m, s);
	}
}
