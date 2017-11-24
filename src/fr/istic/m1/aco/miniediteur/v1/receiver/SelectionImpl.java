package fr.istic.m1.aco.miniediteur.v1.receiver;

/**
 * @author bzherlb
 * Cette classe représente une séléction dans l'éditeur de texte.
 * Une sélection est caractèrisé par un début, une fin et une taille.
 * Les commandes doivent influer sur cette sélection. 
 * 
 * Les instances de cette classes sont immuables.
 */
public class SelectionImpl implements Selection {
	private int startIndex;
	private int length;
	
	/**
	 * 
	 * @param startIndex 
	 * @param length
	 * @throws IllegalArgumentException si l'index de début est <0 ou la longeur est <1
	 */
	public SelectionImpl(int startIndex, int length) {
		if (startIndex < 0 || length < 0) {
			throw new IllegalArgumentException("Paramètres incorrectes dans les indices de la selection dans le constructeur SelectionImpl.");
		}
		
		this.startIndex = startIndex;
		this.length = length;
	}
	
	@Override
	public int getLength() {
		return length;
	}

	@Override
	public int getStartIndex() {
		return startIndex;
	}

	@Override
	public int getEndIndex() {
		if (isEmpty()) {
			return startIndex;
		}
		else {
			return startIndex + length - 1; //-1 car si l'on selectionne 10 caractères depuis l'indice 0, on va bien de 0 à 9.
		}
		
	}
	
	@Override
	public boolean isEmpty() {
		return length == 0;
	}
	
	@Override
	public String toString() {
		return "Début : " + startIndex + " Fin : " + getEndIndex() + " Taille : " + getLength();
	}
}
