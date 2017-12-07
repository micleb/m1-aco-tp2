package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;



/**
 * Mémento de la commande sélectionner. Ce n'est pas vraiment un Memento d'annulation, on rejoue la commande de selection. 
 */
 
public class MementoSelectionner implements Memento {

	private final Moteur m;
	private final Selection s;
	
	public MementoSelectionner(Moteur m, Selection s) {
		if (m == null || s == null) {
			throw new IllegalArgumentException("Null est interdit");
		}
		this.m = m;
		this.s = s;
	}

	@Override
	public void restore() {
		m.selectionner(s);
	}
	
	@Override
	public void cancelRestore() {
		m.selectionner(s);
	}
	
	@Override
	public boolean isIntermediateMemento() {
		return true;
	}
	
	@Override
	public String toString() {
		return "Memento sur la selection : "  + s; 
	}	
}
