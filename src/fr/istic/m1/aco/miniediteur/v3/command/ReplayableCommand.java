package fr.istic.m1.aco.miniediteur.v3.command;

/**
 * Abstraction d'une commande rejouable dans le cadre d'une macro.
 * La méthode executer agit avec les mêmes paramètres que la commande qui à construit cette commande rejouable
 * @see Command#asReplayableCommand() 
 */
public interface ReplayableCommand {
	
	/**
	 * Rejoue cette commande avec les mêmes paramètres.
	 */
	public void executer();
}
