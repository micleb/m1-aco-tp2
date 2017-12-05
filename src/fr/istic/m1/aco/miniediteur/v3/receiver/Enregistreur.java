package fr.istic.m1.aco.miniediteur.v3.receiver;

import fr.istic.m1.aco.miniediteur.v3.command.Command;

public interface Enregistreur {
	/**
	 * 
	 */
	public void demarrer();

	/**
	 * 
	 */
	public void stopper();
	
	/**
	 * 
	 */
	public void rajouter(Command cmd);

	public void rejouer();

	public boolean isOn();
}