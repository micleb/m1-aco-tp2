package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoSelectionner;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

public class Selectionner implements Command {

	private final Moteur moteur;
	private final IHM ui;
	
	public Selectionner(Moteur moteur, IHM ui) {
		this.moteur = moteur;
		this.ui = ui;
	}

	@Override
	public void executer() {
		int start = ui.requestInt("Début de la sélection ?");
		int stop = ui.requestInt("Taille de la sélection ?");
		Selection s = new SelectionImpl(start, stop);
		moteur.selectionner(s);	
	}

	@Override
	public Memento getMemento() {
		return new MementoSelectionner(moteur, moteur.getCurrentSelection());
	}
	
	public String toString() {
		return "Commande selection";
	}
	
	@Override
	public ReplayableCommand asReplayableCommand() {
		return new ReplayableSelectionner(moteur.getCurrentSelection(), moteur);
	}
}
