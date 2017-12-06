package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
/**
 * Représentation abstraite d'une commande.
 * Cette interface générique permet d'executer des commandes sans en connaitre la nature exacte via la méthode executer.
 * Les commandes ne prennent pas de paramètres pour rester générique. 
 * Si une commande à besoin de paramètres, elle les obtiens en les demandants via de l'inversion de dépendance. 
 */
public interface Command {
	
	/**
	 * Execute cette commande, en affectant l'état du moteur. 
	 */
	public void executer();
	
	/**
	 * @return Le mémento correspondant à cette commande en particulier, dans le but de potebtiellement l'annuler.
	 */
	public Memento getMemento();
	
	/**
	 * @return Cette commande sous une forme rejouable à l'identique. 
	 */
	public ReplayableCommand asReplayableCommand();
}
