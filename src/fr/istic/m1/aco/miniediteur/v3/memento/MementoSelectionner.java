package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class MementoSelectionner implements Memento {

	private Moteur m;
	private Selection s;
	
	public MementoSelectionner(Moteur m, Selection s) {
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
