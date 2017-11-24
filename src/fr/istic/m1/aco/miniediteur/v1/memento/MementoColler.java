package fr.istic.m1.aco.miniediteur.v1.memento;

import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class MementoColler implements Memento {

	private final String pastedBuffer;
	private final Selection pasteDestination;
	private final Moteur m;
	private String contentToRestore;
	
	public MementoColler(Moteur m, String removedContent) {
		this.pastedBuffer = m.getPresspapierContent();
		this.pasteDestination = m.getCurrentSelection();
		this.m = m;
		this.contentToRestore = removedContent; 
	}
	
	@Override
	public void restore() {	
		Selection s2 = new SelectionImpl(pasteDestination.getStartIndex(),pastedBuffer.length());
		m.selectionner(s2);
		m.supprimer();
		Selection restoreDst = new SelectionImpl(pasteDestination.getStartIndex(),0);
		m.selectionner(restoreDst);
		m.inserer(contentToRestore);
	}
}
