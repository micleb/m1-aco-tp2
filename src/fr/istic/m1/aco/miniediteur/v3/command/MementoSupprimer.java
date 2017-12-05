package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

public class MementoSupprimer implements Memento {
	private Selection lastSelectionDeletion;
	private String contentToRestore;
	private Moteur m;
	
	public MementoSupprimer(Moteur m, Selection lastSelectionDeletion, String deletedContent) {
		this.m = m;
		this.lastSelectionDeletion = lastSelectionDeletion;
		this.contentToRestore = deletedContent;
	}
	
	@Override
	public void restore() {
		Selection restorationPoint = new SelectionImpl(lastSelectionDeletion.getStartIndex(), 0);
		m.selectionner(restorationPoint);
		m.inserer(contentToRestore);
	}

	@Override
	public boolean isIntermediateMemento() {
		return false;
	}

	@Override
	public void cancelRestore() {
		m.selectionner(lastSelectionDeletion);
		m.supprimer();
	}
}
