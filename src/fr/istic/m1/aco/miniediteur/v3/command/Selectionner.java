package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoSelectionner;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

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
