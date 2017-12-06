package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.command.Command;
/**
 * Abstraction de l'historique des commandes effectuées. 
 * Cette interface offre des opérations pour pouvoir les défaires et les refaires.
 * 
 * Il faut d'abord signaler à l'historique qu'une commande a été effectuée via la méthode registerCommand(),
 * puis effectuer les opérations défaire,refaire respectivement par undo redo.
 * 
 * Chaque commande doit être mémorisée, et ce immédiatement après execution
 * ce n'est pas au client de décider si la commande doit avoir un traitement ou un ordre spéciale.  
 */
public interface CommandsHistoric {
	
	/**
	 * @param cmd La commande à mémoriser pour pouvoir la rejouer plus tard.
	 * @precondition Il faut effectuer l'enregistrement de la commande cmd immédiatement après
	 * l'avoir exécutée. 
	 */
	public void registerCommand(Command cmd);

	/**
	 * Annule la dernière commande mémorisée qui modifie l'état du moteur en restorant l'état du contenu du moteur avant son éxécution. 
	 * @see CommandsHistoric#registerCommand(Command)
	 * @precondition Il doit y avoir quelque chose à annuler, c'est à dire que commandsHistoricsSize() > 0.
	 */
	public void undo();

	
	/**
	 * Rejoue la dernière commande précédement annulée via un appel à undo(), en restorant l'état du contenu du moteur avant annulation.
	 * Puisque la commande est rejoué, elle est automatiquement de nouveau mémorisée via registerCommand.
	 * Cela permet de revenir sur sa décision et d'annuler la commande de nouveau.
	 * @precondition Il doit y avoir quelque chose à rejouer, c'est à dire que cancelationHistoricSize() > 0.
	 */
	public void redo();
	
	
	/**
	 * Permet de savoir combien de commande sont sauvegardées dans l'historique.
	 * Chaque appel à registerCommand augmente cette valeur de 1.
	 * Chaque appel à undo la diminue de 1 puisqu'une fois qu'une commande est annulé via un CTRL-Z,
	 * on la supprime de l'historique.
	 * @return Le nombre de commande annulable dans l'historique.
	 */
	public int commandsHistoricsSize();
	
	/**
	 * Permet de savoir combien d'annulation de commande sont mémorisées dans l'historique.
	 * Chaque appel à undo() annule une commande donc augmente cette valeur de 1.
	 * Chaque appel à redo diminue cette valeur de 1 puisque l'on refais une commande annulée.
	 * @return Le nombre de commande restorable dans l'historique.
	 */
	public int cancelationHistoricSize();
}
