package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

/**
 * Mémento d'annulation de la commande supprimer. On restore l'ancien contenu supprimée à la place où il était.
 *
 */
public class MementoSupprimer implements Memento {
	private final Selection lastSelectionDeletion;
	private final String contentToRestore;
	private final Moteur m;
	
	/**
	 * @param m Le moteur sur lequel a eu lieu l'execution de la commande.
	 * @param lastSelectionDeletion La selection sur laquelle la commande supprimer à été effectuée.
	 * @param deletedContent Le contenu supprimer par la commande supprimer
	 */
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
