package fr.istic.m1.aco.miniediteur.v1.memento;

import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public class MementoInserer implements Memento {

	private final String inseredContent;
	private final Selection insertion;
	private final Moteur m;
	
	
	public MementoInserer(String inseredContent, Selection insertion, Moteur m) {
		this.inseredContent = inseredContent;
		this.insertion = insertion;
		this.m = m;
	}

	@Override
	public void restore() {
		m.selectionner(insertion);
		m.supprimer();
	}

}
