package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;

/**
 * Commande qui copie le contenu de la sélection actuelle dans le presse papier du moteur.
 * L'ancien contenu du presse-papier est alors écrasé.
 */
public class Copier implements Command, ReplayableCommand {
	
	private final Moteur moteur;
	
	public Copier(Moteur moteur) {
		this.moteur = moteur;
	}
	
	@Override
	public void executer() {
		moteur.copier();
	}
	
	@Override
	public Memento getMemento() {
		return EmptyMemento.getUniqueInstance();
	}
	
	
	@Override
	public String toString() {
		return "Commande copier de la selection : " + moteur.getCurrentSelection() 
		+ " qui contient " + moteur.getSelectedContent();
	}

	@Override
	public ReplayableCommand asReplayableCommand() {
		return this;
	}
}
