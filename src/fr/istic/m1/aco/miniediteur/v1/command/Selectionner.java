package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.memento.MementoSelectionner;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public class Selectionner implements Command {

	private Selection selection;
	private Moteur moteur;
	
	public Selectionner(Moteur moteur, Selection s) {
		this.selection = s;
		this.moteur = moteur;
	}

	@Override
	public void executer() {
		moteur.selectionner(selection);	
	}

	@Override
	public Memento getMemento() {
		return new MementoSelectionner(moteur, selection);
	}
	
	public String toString() {
		return "Commande selection : " + this.selection;
	}
}
