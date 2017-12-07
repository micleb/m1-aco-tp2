package fr.istic.m1.aco.miniediteur.v3.memento;

/**
 * L'interface Memento permet de representer la sauvegarde d'une commande effectuée par le passé.
 * Un memento peut être vu comme une commande dans le sens inverse. 
 * En executant un mémento juste après l'execution d'une commande, on doit retrouver l'état initial.
 * 
 * Les memento doivent être joué dans un ordre cohérent : Si vous executer la commande A B puis C, il faut invoquer 
 * les mementos dans le sens inverse : memento C, B puis A, sinon le comportement est indéfini.
 *  
 *  Il est fortement recommandé au classes clientes ayant besoin de la gestion d'annulation de ne pas directement
 *  utiliser les Memento mais de passer par une interface intermédiaire totalement dédiée à cette tache, par exemple CommandsHistoricImpl ou votre propre iméplentation de l'interface CommandsHistoric.
 */
public interface Memento {

	/**
	 * Annule la commande représenté par ce memento.
	 * @precondition Pour que cette commande s'effectue correctement, il faut s'assurer que la commande restoré est bien la dernière commande executée.
	 * C'est à dire que si on effectue les commandes A,B puis C dans cette ordre, il ne faut pas chercher à restorer la commande C avant la commande B.
	 * Si cette ordre n'est pas respecté, le comportement est indéfini.
	 */
	public void restore();
	
	/**
	 * Un memento est dit "intermediaire" si il n'affecte pas directement le contenu du texte de l'éditeur.
	 * 
	 * C'est le cas pour les commandes qui préparent à une modification ultérieure mais ne modifie pas elle même le
	 * contenu du moteur. C'est par exemple le cas de copier ou de sélectionner, ainsi que tout memento vide.
	 * 
	 * Bien qu'un memento intermediaire n'affecte jamais le contenu du texte du moteur, il peut tout à fait modifier l'état du 
	 * moteur sous d'autres aspects, c'est à dire l'état du presse papier ou de la sélection courrante.
	 *  
	 * @return true si ce mémento est un mémento intermédiaire.
	 */
	public boolean isIntermediateMemento();

	/**
	 * Restore la commande précédement annulée par la méthode restore();
	 */
	void cancelRestore();
}
