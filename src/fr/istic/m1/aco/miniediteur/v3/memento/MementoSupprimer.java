package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class MementoSupprimer implements Memento {

	private Moteur m;
	private Selection s;
	private String backup;
	
	public MementoSupprimer(Moteur m, Selection s, String backup) {
		super();
		this.m = m;
		this.s = s;
		this.backup = backup;
	}
	
	@Override
	public void restore() {
		m.selectionner(s);
		m.inserer(backup);
	}

	@Override
	public boolean isIntermediateMemento() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString() {
		return "Memento sur la selection : "  + s; 
	}

}
