package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoColler;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;


public class Coller implements Command, ReplayableCommand {
	private Moteur m;
	Selection lastSelection;
	String lastRemovedContent;

	public Coller(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer(){
		lastRemovedContent = m.getSelectedContent();
		lastSelection = m.getCurrentSelection();
		m.coller();
	}
	
	@Override
	public Memento getMemento() {
			return new MementoColler(m, lastRemovedContent, lastSelection);
	}
	
	@Override
	public String toString() {
		return "Commande coller dans la selection : " + m.getCurrentSelection() 
		+ " qui contient " + m.getSelectedContent() + " \n alors remplacé par le contenu du press-papier qui est :"
		+ " " + m.getPresspapierContent();
	}

	@Override
	public ReplayableCommand asReplayableCommand() {
		return this;
	}
}