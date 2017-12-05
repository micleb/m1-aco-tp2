package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoInserer;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class Delete implements Command {

	private Moteur m;
	private String lastDeletedContent;
	private Selection lastSelectionDeletion;
	
	public Delete(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer() {
		lastDeletedContent = m.getSelectedContent();
		lastSelectionDeletion = m.getCurrentSelection();
		m.supprimer();
	}

	@Override
	public Memento getMemento() {
		return new MementoInserer(lastSelectionDeletion, "", lastDeletedContent, m);
	}

	@Override
	public ReplayableCommand asReplayableCommand() {
		return new ReplayableInserer(m.getCurrentSelection(), "", m);
	}

}
