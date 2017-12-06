package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoCouper;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

/**
 * Commande qui coupe le contenu de la sélection actuelle dans le presse papier du moteur.
 * Le contenu de la sélection est supprimé du contenu du texte de l'éditeur.
 * L'ancien contenu du presse-papier est alors écrasé.
 */
public class Couper implements Command, ReplayableCommand {

	private final Moteur m;
	private Selection lastSelection;
	
	public Couper(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer() {
		lastSelection = m.getCurrentSelection();
		m.couper();
	}

	@Override
	public Memento getMemento() {
		return new MementoCouper(m, lastSelection, m.getPresspapierContent());	
	}
	
	@Override
	public String toString() {
		return "Commande couper de la selection : " + m.getCurrentSelection() 
		+ " qui contient " + m.getSelectedContent();
	}

	@Override
	public ReplayableCommand asReplayableCommand() {
		return this;
	}
}
