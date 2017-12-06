package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoInserer;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

/**
 * Commande qui insère du texte entrée par l'utilisateur dans la sélection courrante.
 * Si la sélection est une sélection vide, on ajoute le contenu entré à sa suite.
 * Dans le cas d'une sélection non vide au moment de l'insertion, on écrase l'ancien contenu selectionné.
 */
public class Inserer implements Command {

	private final Moteur moteur;
	private final IHM ui;
	
	private String lastInseredContent;
	private String lastDeletedContent;
	private Selection lastSelection;
	
	public Inserer(Moteur moteur, IHM ui) {
		if(moteur == null || ui == null) {
			throw new IllegalArgumentException("Null parameters are not allowed");
		}
		this.ui = ui;
		this.moteur = moteur;
	}
	
	@Override
	public void executer() {
		String content = ui.requestString("Tapez le contenu à inserer : ");
		lastDeletedContent = moteur.getSelectedContent();
		lastInseredContent = content;
		lastSelection = moteur.getCurrentSelection();
		
		moteur.inserer(content);
	}

	@Override
	public Memento getMemento() {
		return new MementoInserer(lastSelection, lastInseredContent, lastDeletedContent, moteur);		
	}
	
	@Override
	public String toString() {
		return "Commande inserer"; 
	}

	@Override
	public ReplayableCommand asReplayableCommand() {
		return new ReplayableInserer(lastSelection, lastInseredContent, moteur);
	}
}
