package fr.istic.m1.aco.miniediteur.v1.receiver;

/**
 * @author bzherlb
 * Représente une sélection dans l'éditeur de texte.
 * On ne parle pas ici du contenu sélectionné en lui même mais de la nature de la sélection : ses bornes et sa taille.
 *
 * Une selection est immuable.
 */
public interface Selection {
	/**
	 * La taille de la sélection.
	 * Elle peut-être égale à zéro pour représenter une séléction vide, c'est à dire quand 
	 * le curseur est positionné quelque part mais qu'il n'y a pas de caractère en particulier de selectionné.
	 * @post getLength() >= 0
	 * @return La taille du contenu séléctionné.
	 */
	public int getLength();
	
	
	/**
	 * @return L'indice de départ de cette sélection.
	 * @postcondition return value > 0.
	 */
	public int getStartIndex();
	
	
	/**
	 * La position de la fin de la selection.
	 * @return L'indice de fin de cette selection.
	 */
	public int getEndIndex();

	/**
	 * @return TRUE si la selection est vide, sinon faux.
	 */
	boolean isEmpty();
	
	@Override
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();
}
