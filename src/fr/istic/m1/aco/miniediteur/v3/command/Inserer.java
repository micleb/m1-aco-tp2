package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoInserer;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class Inserer implements Command {

	private Moteur moteur;
	private IHM ui;
	
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
