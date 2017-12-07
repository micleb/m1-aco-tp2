package fr.istic.m1.aco.miniediteur.v3.memento;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.command.Coller;
import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.Copier;
import fr.istic.m1.aco.miniediteur.v3.command.Couper;
import fr.istic.m1.aco.miniediteur.v3.command.Delete;
import fr.istic.m1.aco.miniediteur.v3.command.Inserer;
import fr.istic.m1.aco.miniediteur.v3.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v3.invoker.StubIHM;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoricImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;

public class CommandsHistoricTest {
	private Moteur m;
	private CommandsHistoric cmds = new CommandsHistoricImpl();
	private static final String INITIAL_CONTENT = "123456789ABCDEF";
	
	private StubIHM ui;
	private Command selectionner; 
	private Command couper;
	private Command coller;
	private Command copier;
	private Command inserer;
	private Command supprimer;
	
	@Before
	public void setUp() {
		ui = new StubIHM();
		StringBuffer b = new StringBuffer(INITIAL_CONTENT);
		this.m = new MoteurImpl(b);	
		
		couper = new Couper(m);
		coller = new Coller(m);
		copier = new Copier(m);
		selectionner = new Selectionner(m, ui);
		inserer = new Inserer(m, ui);
		supprimer = new Delete(m);
	}
	
	private void makeSelection(int start, int size) {
		ui.addFakeUserResponse(start);
		ui.addFakeUserResponse(size);
		selectionner.executer();
		cmds.registerCommand(selectionner);
	}
	@Test
	public void testComportementIntermediaire() {
		//On fait selectionner et copier (i.e memento intermédiaire) avant et après
		//couper pour vérifier que cela ne perturbe pas undo().
		makeSelection(9,2);
		makeSelection(4,4);
		copier.executer();
		cmds.registerCommand(copier);
		couper.executer();
		cmds.registerCommand(couper);
		assertEquals("5678", m.getPresspapierContent());
		copier.executer();
		cmds.registerCommand(copier);
		assertEquals("", m.getPresspapierContent());
		
		makeSelection(0, 1);
		makeSelection(5, 1);
		
		//Comportement attendu : un seul UNDO doit suffire à restaurer le contenu précedent
		//du texte du moteur, malgrès les opérations intermédiaires.Co
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
		
	@Test
	public void testCollerSimpleUndo() {		
		makeSelection(1,4);
		copier.executer();
		cmds.registerCommand(copier);
		assertEquals("2345", m.getPresspapierContent());
		
		makeSelection(9, 0);
		coller.executer();
		cmds.registerCommand(coller);
		assertEquals("1234567892345ABCDEF", m.getContent());
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testCollerSimpleRedo() {
		testCollerSimpleUndo();
		cmds.redo();
		assertEquals("1234567892345ABCDEF", m.getContent());
	}
	
	final String stage1CollerMultiple = "1234567892345ABCDEF";
	final String stage2CollerMultiple = "2345234567892345ABCDEF"; 
	final String stage3CollerMultiple = "2345234567892345ABCD2345EF";
	
	@Test
	public void testCollerMultipleUndo() {
		makeSelection(1, 4);
		copier.executer();
		cmds.registerCommand(copier);
		assertEquals("2345", m.getPresspapierContent());
		
		//ETAPE 1 : A partir de l'indice 9, on fait une selection de taille 0, on colle donc "2345" à la suite de "9" sans suppression.
		makeSelection(9, 0);
		coller.executer();
		cmds.registerCommand(coller);
		assertEquals(stage1CollerMultiple, m.getContent());
		
		//ETAPE 2 : A partir de l'indice 0, on fait une selection de taille 1, on colle donc "2345" en supprimant "1"
		makeSelection(0, 1);
		coller.executer();
		cmds.registerCommand(coller);
		assertEquals(stage2CollerMultiple, m.getContent());
		
		//ETAPE 3 :A partir de l'indice 20, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		makeSelection(20, 0);
		coller.executer();
		assertEquals(stage3CollerMultiple, m.getContent());
		cmds.registerCommand(coller);
		
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
	public void testCouperSimpleUndo() {
		makeSelection(1, 4);
		couper.executer();
		cmds.registerCommand(couper);
		assertEquals("16789ABCDEF", m.getContent());
		assertEquals("2345", m.getPresspapierContent());
		
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
		assertEquals("2345", m.getPresspapierContent());
	}
	
	@Test
	public void testCouperSimpleRedo() {
		testCouperSimpleUndo();
		cmds.redo();
		assertEquals("16789ABCDEF", m.getContent());
	}
	
	@Test
	public void testCouperMultipleUndo() {
		makeSelection(1, 4);
		
		//ETAPE 1 : On execute un couper des caractères 1 à 4.
		couper.executer();
		cmds.registerCommand(couper);
		assertEquals("2345", m.getPresspapierContent());
		assertEquals(stage1CouperMultiple, m.getContent());
		
		//ETAPE 2 : A partir de l'indice 9, on fait une selection de taille 0, on colle donc "2345" à la suite de "D" sans suppression.
		makeSelection(9, 0);
		coller.executer();
		assertEquals(stage2CouperMultiple, m.getContent());
		cmds.registerCommand(coller);
		
		//ETAPE 3 : A partir de l'indice 0, on fait une selection de taille 1, on colle donc "2345" en supprimant "1".
		makeSelection(0, 1);
		coller.executer();
		cmds.registerCommand(coller);
		assertEquals(stage3CouperMultiple, m.getContent());
				
		//ETAPE 4 :A partir de l'indice 18, on fait une selection de taille 0, on colle donc "2345" à la suite de "F" sans suppression.
		makeSelection(18, 0);
		coller.executer();
		cmds.registerCommand(coller);
		assertEquals(stage4CouperMultiple, m.getContent());
		
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
	public void testCouperMultipleUndoRedo() {
		testCouperMultipleRedo();
		//Quelques CTRL-Z, CTRL-Y suivant la même logique.
		cmds.undo();
		assertEquals(stage3CouperMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage4CouperMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage3CouperMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage2CouperMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1CouperMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2CouperMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1CouperMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2CouperMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage3CouperMultiple, m.getContent());
	}
	@Test
	public void testUndoInserer(){		
		makeSelection(9, 3);
		this.ui.addFakeUserResponse("XYZT");
		inserer.executer();
		assertEquals("123456789XYZTDEF", m.getContent());
		cmds.registerCommand(inserer);
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testInsererRedo() {
		testUndoInserer();
		cmds.redo();
		assertEquals("123456789XYZTDEF", m.getContent());
	}
	
	final String stage1InsererMultiple = "123456789XYZTDEF";
	final String stage2InsererMultiple = "12345!..+6789XYZTDEF";
	final String stage3InsererMultiple = "12345!..+6789XYZTDEF000";
	final String stage4InsererMultiple = "12345!..+6789XYZTDEF00*";
			
	@Test
	public void testInsererMultipleUndo(){	
		makeSelection(9, 3);
		this.ui.addFakeUserResponse("XYZT");
		inserer.executer();
		cmds.registerCommand(inserer);
		assertEquals(stage1InsererMultiple, m.getContent());
			
		makeSelection(5, 0);
		this.ui.addFakeUserResponse("!..+");
		inserer.executer();
		cmds.registerCommand(inserer);
		assertEquals(stage2InsererMultiple, m.getContent());
		
		makeSelection(20, 0);
		this.ui.addFakeUserResponse("000");
		inserer.executer();
		cmds.registerCommand(inserer);
		assertEquals(stage3InsererMultiple, m.getContent());
				
		makeSelection(22, 1);
		this.ui.addFakeUserResponse("*");
		inserer.executer();
		cmds.registerCommand(inserer);
		assertEquals(stage4InsererMultiple, m.getContent());
		
		cmds.undo();
		assertEquals(stage3InsererMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage2InsererMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1InsererMultiple, m.getContent());
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testInsererMultipleRedo() {
		testInsererMultipleUndo();
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
	
	@Test 
	public void testSupprimerSimpleUndo() {
		makeSelection(6, 4);
		supprimer.executer();
		cmds.registerCommand(supprimer);
		assertEquals("123456BCDEF", m.getContent());
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test 
	public void testSupprimerSimpleRedo() {
		testSupprimerSimpleUndo();
		cmds.redo();
		assertEquals("123456BCDEF", m.getContent());
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	final String stage1SupprimerMultiple = "123456BCDEF";
	final String stage2SupprimerMultiple = "123456BCDE";
	final String stage3SupprimerMultiple = "1234CDE"; 
	final String stage4SupprimerMultiple = "34CDE";
	
	@Test 
	public void testSupprimerMultipleUndo() {
		//On supprime 789A
		makeSelection(6, 4);
		supprimer.executer();
		cmds.registerCommand(supprimer);
		assertEquals(stage1SupprimerMultiple, m.getContent());
		
		//On supprime le F à la fin.
		makeSelection(10, 1);
		supprimer.executer();
		cmds.registerCommand(supprimer);
		assertEquals(stage2SupprimerMultiple, m.getContent());
		
		//On supprime 56B
		makeSelection(4, 3);
		supprimer.executer();
		cmds.registerCommand(supprimer);
		assertEquals(stage3SupprimerMultiple, m.getContent());
		
		//On supprime 12
		makeSelection(0, 2);
		supprimer.executer();
		cmds.registerCommand(supprimer);
		assertEquals(stage4SupprimerMultiple, m.getContent());
		
		//On restore 12
		cmds.undo();
		assertEquals(stage3SupprimerMultiple, m.getContent());
		//On restore 56B
		cmds.undo();
		assertEquals(stage2SupprimerMultiple, m.getContent());
		//On restore le f à la fin.
		cmds.undo();
		assertEquals(stage1SupprimerMultiple, m.getContent());
		//On restore 789A
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testSupprimerMultipleRedo() {
		testSupprimerMultipleUndo();
		//Après avoir restoré le contenu initial, on refait l'étape 1 (equivalent de CTRL+Y),
		//donc on doit se retrouver avec le même contenu qu'a la fin de l'étape 1. 
		cmds.redo();
		assertEquals(stage1SupprimerMultiple, m.getContent());
		//Restoration de la commande suivante, donc on retrouve le conteu de l'étape 2.
		cmds.redo();
		assertEquals(stage2SupprimerMultiple, m.getContent());
		//Puisqu'on a fait 3 redo après nos 3 undo, c'est comme si on avait jamais fait d'undo.
		//Donc on retrouve à la situation de l'étape 3.
		cmds.redo();
		assertEquals(stage3SupprimerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage4SupprimerMultiple, m.getContent());
	}
	
	@Test 
	public void testSupprimerMultipleUndoRedo() {
		testSupprimerMultipleRedo();
		//Quelques CTRL-Z, CTRL-Y suivant la même logique.
		cmds.undo();
		assertEquals(stage3SupprimerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage4SupprimerMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage3SupprimerMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage2SupprimerMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1SupprimerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2SupprimerMultiple, m.getContent());
		cmds.undo();
		assertEquals(stage1SupprimerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage2SupprimerMultiple, m.getContent());
		cmds.redo();
		assertEquals(stage3SupprimerMultiple, m.getContent());
	}
	
	final String stage1Multiple = "123456ABCDEF"; //Suppression
	final String stage2Multiple = "*123456ABCDEF"; //Insertion (selection vide)
	final String stage3Multiple = "GHIJ123456ABCDEF"; //Insertion (selection non vide)
	final String stage4Multiple = "123456ABCDEF"; //Couper
	final String stage5Multiple = "123456ABCDEFGHIJ"; //Coller vide
	final String stage6Multiple = "123456ABCDEFGHIJ*"; //Inserer + Copier
	final String stage7Multiple = "123456A*DEFGHIJ*"; //Coller non vide
	
	@Test 
	public void testMultipleCmdMultipleUndo() {
		makeSelection(6, 3);
		supprimer.executer();
		cmds.registerCommand(supprimer);
		assertEquals(stage1Multiple, m.getContent());
		
		
		makeSelection(8, 1);
		makeSelection(0, 0);
		ui.addFakeUserResponse("*");
		inserer.executer();
		cmds.registerCommand(inserer);
		assertEquals(stage2Multiple, m.getContent());
		
		makeSelection(12, 0);
		makeSelection(0, 1);
		ui.addFakeUserResponse("GHIJ");
		inserer.executer();
		cmds.registerCommand(inserer);
		assertEquals(stage3Multiple, m.getContent());
		
		makeSelection(5, 2);
		makeSelection(0, 4);
		couper.executer();
		cmds.registerCommand(couper);
		assertEquals(stage4Multiple, m.getContent());
		
		makeSelection(7, 2);
		makeSelection(12, 0);
		coller.executer();
		cmds.registerCommand(coller);
		assertEquals(stage5Multiple, m.getContent());
		
		makeSelection(16, 0);
		ui.addFakeUserResponse("*");
		inserer.executer();
		cmds.registerCommand(inserer);
		makeSelection(7, 2);
		makeSelection(16, 1);
		copier.executer();
		cmds.registerCommand(copier);
		assertEquals(stage6Multiple, m.getContent());
		assertEquals("*", m.getPresspapierContent());
		
		makeSelection(12, 0);
		makeSelection(7, 2);
		coller.executer();
		cmds.registerCommand(coller);
		assertEquals(stage7Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage6Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage5Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage4Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage3Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage2Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage1Multiple, m.getContent());
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
	}
	
	@Test
	public void testMultipleCmdMultipleRedo() {
		testMultipleCmdMultipleUndo();
		cmds.redo();
		assertEquals(stage1Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage2Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage3Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage4Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage5Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage6Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage7Multiple, m.getContent());
	}
	
	@Test
	public void testMultipleCmdMultipleUndoRedo() {
		testMultipleCmdMultipleRedo();
		cmds.undo();
		assertEquals(stage6Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage7Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage6Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage5Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage6Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage5Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage4Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage3Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage4Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage3Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage2Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage1Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage2Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage3Multiple, m.getContent());
		
		cmds.undo();
		assertEquals(stage2Multiple, m.getContent());
		cmds.undo();
		assertEquals(stage1Multiple, m.getContent());
		cmds.undo();
		assertEquals(INITIAL_CONTENT, m.getContent());
		cmds.redo();
		assertEquals(stage1Multiple, m.getContent());
		cmds.redo();
		assertEquals(stage2Multiple, m.getContent());
	}
	
}
