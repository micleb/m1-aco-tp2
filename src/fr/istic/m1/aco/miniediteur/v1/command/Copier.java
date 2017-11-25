package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;

public class Copier implements Command {
	
	private Moteur moteur;
	
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
}
