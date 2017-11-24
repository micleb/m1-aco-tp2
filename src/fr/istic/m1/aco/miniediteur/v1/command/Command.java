package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.Memento;

public interface Command {
	
	/**
	 * Execute cette commande, en affectant l'état du moteur. 
	 */
	public void executer();
	
	/**
	 * @return Le mémento correspondant à cette commande en particulier, dans le but de potebtiellement l'annuler.
	 */
	public Memento getMemento();
}