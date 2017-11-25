package fr.istic.m1.aco.miniediteur.v1.memento;

import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class MementoInserer implements Memento {

	private final int insertionLenght;
	private final String deletedContent;
	private final Selection insertion;
	private final Moteur m;
		
	public MementoInserer(Selection insertion, int length, String deletedContent, Moteur m) {
		this.insertionLenght = length;
		this.deletedContent = deletedContent;
		this.insertion = insertion;
		this.m = m;
	}

	@Override
	public void restore() {
		m.selectionner(getSelectionOfNewContent());
		m.inserer(deletedContent);
		m.selectionner(insertion);
	}
	
	private Selection getSelectionOfNewContent() {
		return new SelectionImpl(insertion.getStartIndex(), insertionLenght);
	}
	
	@Override
	public boolean isIntermediateMemento() {
		return false;
	}

}
