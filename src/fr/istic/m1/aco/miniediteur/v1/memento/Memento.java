package fr.istic.m1.aco.miniediteur.v1.memento;

/**
 * @author bzherlb
 * L'interface Memento permet de representer la sauvegarde d'une commande effectué par le passé.
 * Un memento peut être vu comme une commande dans le sens inverse.
 */
public interface Memento {

	/**
	 * Annule la commande représenté par ce memento.
	 * @precondition Pour que cette commande s'effectue correctement, il faut s'assurer que la commande restoré est bien la dernière commande executée.
	 * C'est à dire que si on effectue une commande a,b puis c dans cette ordre, il ne faut pas chercher à restorer la commande C avant la commande B.
	 * Si cette ordre n'est pas respecté, le comportement est indéfini.
	 */
	public void restore();
}
