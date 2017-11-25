package fr.istic.m1.aco.miniediteur.v1.memento;

/**
 * @author bzherlb
 * Representation d'un memento vide, il s'agit d'une instance du design pattern "Objet speciale".
 * 
 * Pour certaines commandes, l'annulation (CTRL-Z) n'a pas de sens, c'est par exemple le cas de copier.
 * Plutôt que d'utiliser des pointeurs null dans ce genre de cas, il vaut mieux utiliser cet objet special. 
 * 
 * Du point de vu des classes ayant une dépendance vers l'interface Memento, les instances de cette classes sont des mémentos comme les autres.
 */
public class EmptyMemento implements Memento {

	private static Memento uniqueInstance = null;
	
	private EmptyMemento() {
		
	}
	
	public static Memento getUniqueInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new EmptyMemento();
		}
		return uniqueInstance;
	}

	@Override
	public void restore() {
		// Rien à restorer.
	}

	@Override
	public boolean isIntermediateMemento() {
		return true;
	}
	
	@Override
	public String toString() {
		return "Memento vide sans aucun effet.";
	}
}
