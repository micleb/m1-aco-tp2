package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoInserer;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

/**
 * Commande qui supprime le contenu de la sélection actuelle du texte de l'éditeur.
 */
public class Delete implements Command {

	private final Moteur m;
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
