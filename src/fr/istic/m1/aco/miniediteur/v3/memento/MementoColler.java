package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

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
		Selection s2 = getSelectionResultante();
		m.selectionner(s2);
		m.supprimer();
		Selection restoreDst = new SelectionImpl(pasteDestination.getStartIndex(),0);
		m.selectionner(restoreDst);
		m.inserer(contentToRestore);
		m.selectionner(pasteDestination);
	}
	
	@Override
	public boolean isIntermediateMemento() {
		return false;
	}
	
	/*
	 * Renvoie une selection qui contient tout le contenu que l'on a coller.
	 * C'est le même indice de départ que la selection écrasée avant l'execution de la commande,
	 * mais la fin est ici l'indice de fin du contenu coller, pas celui de la selection initiale.
	 */
	private Selection getSelectionResultante() {
		return new SelectionImpl(pasteDestination.getStartIndex(),pastedBuffer.length());
	}
	
	@Override
	public String toString() {
		return "Memento d'annulation de la commande coller."
		+ "A la selection : " + getSelectionResultante()
		+ " qui contient " + m.getContentAt(getSelectionResultante())
		+ " on restore l'ancien contenue qui est "
		+ this.contentToRestore;
	}
}
