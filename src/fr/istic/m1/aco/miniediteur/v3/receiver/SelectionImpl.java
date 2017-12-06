package fr.istic.m1.aco.miniediteur.v3.receiver;

/**
 * @author bzherlb
 * Cette classe représente une séléction dans l'éditeur de texte.
 * Une sélection est caractèrisée par un début, une fin et une taille.
 * Les commandes doivent influer sur cette sélection. 
 * 
 * Les instances de cette classes sont immuables.
 */
public class SelectionImpl implements Selection {
	private final int startIndex;
	private final int length;
	
	/**
	 * Constructeur.
	 * 
	 * @param startIndex L'indice de départ de cette sélection, inclue.
	 * @param length La taille de la sélection. 
	 * @precondition startIndex >= 0 
	 * @precondition length >= 0
	 * @throws IllegalArgumentException si une précondition n'est pas vérifié
	 */
	public SelectionImpl(int startIndex, int length) {
		if (startIndex < 0 || length < 0) {
			throw new IllegalArgumentException("Paramètres incorrectes dans les indices de la selection dans le constructeur SelectionImpl : startIndex = " + startIndex +  " lenght = " + length);
		}
		this.startIndex = startIndex;
		this.length = length;
		
		checkInvariants();
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
			return startIndex + length; //-1 car si l'on selectionne 10 caractères depuis l'indice 0, on va bien de 0 à 9.
		}
		
	}
	
	@Override
	public boolean isEmpty() {
		return length == 0;
	}
	
	@Override
	public String toString() {
		return "Instance de Selection : \n Début : " + startIndex + "\n Fin : " + getEndIndex() + "\n Taille : " + getLength() + "\n";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + startIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectionImpl other = (SelectionImpl) obj;
		if (length != other.length)
			return false;
		if (startIndex != other.startIndex)
			return false;
		return true;
	}

	private void checkInvariants() {
		if (!invariantsOk()) {
			throw new IllegalStateException("La sélection a un état incohérent : " + this.toString());
		}
	}
	public boolean invariantsOk() {
		return getStartIndex() <= getEndIndex()
				&& consitencyLengthIndex() 
				&& consistencyEmpty();
	}

	private boolean consistencyEmpty() {
		if (isEmpty()) {
			return getStartIndex() == getEndIndex() && getLength() == 0;
		}
		
		if (getStartIndex() == getEndIndex() && getLength() == 0) {
			return isEmpty();
		}
		return true;
	}

	private boolean consitencyLengthIndex() {
		if (getLength() == 0) {
			return getStartIndex() == getEndIndex() && isEmpty();
		}
		
		// On faut faire la vérification dans les deux sens, 
		// sinon une sélection de taille non vide avec pourtant des indices de début et de fin différents
		// serait faussement considérée comme valide.
		if (getStartIndex() == getEndIndex()) {
			return getLength() == 0 && isEmpty();
		}
		
		return getEndIndex() - getStartIndex() == getLength(); 
	}
}
