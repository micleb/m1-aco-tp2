package fr.istic.m1.aco.miniediteur.v3.invoker;

/**
 * Abstraction de 'lihm. Offre des fonctions permettant aux controleurs et aux commandes d'afficher de linformation 
 * à destination de l'utilisateur ou de récupérer des entrées de sa part, sans que l'IHM n'ait besoin de savoir 
 * exactement la nature des entrées.
 *
 */
public interface IHM {
	
	/**
	 * Démarre l'IHM. 
	 */
	void start();

	
	/**
	 * @param prompt Le message à afficher à l'utilisateur, par exemple "Fin de la selection?".
	 * @return L'entier choisi par l'utilisateur.
	 */
	public int requestInt(String prompt);

	/**
	 * @param prompt Le message à afficher à l'utilisateur, par exemple "Contenu à inserer?".
	 * @return La chaîne entrée à l'utilisateur en réponse.
	 */
	public String requestString(String prompt);


	/**
	 * Méthode permettant d'afficher un message à l'utilisateur.
	 * @param string Un message d'information.
	 */
	void notifyUser(String string);
}
