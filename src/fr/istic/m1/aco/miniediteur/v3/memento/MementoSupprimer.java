package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

/**
 * Mémento d'annulation de la commande supprimer. On restore l'ancien contenu supprimé à la place où il était.
 */
public class MementoSupprimer implements Memento {
	private final Selection lastSelectionDeletion;
	private final String contentToRestore;
	private final Moteur m;
	
	/**
	 * Constructeur.
	 * @param m Le moteur sur lequel a eu lieu l'execution de la commande.
	 * @param lastSelectionDeletion La selection sur laquelle la commande supprimer à été effectuée.
	 * @param deletedContent Le contenu supprimer par la commande supprimer.
	 */
	public MementoSupprimer(Moteur m, Selection lastSelectionDeletion, String deletedContent) {
		if (m == null || lastSelectionDeletion == null || deletedContent == null) {
			throw new IllegalArgumentException("Null est interdit");
		}
		this.m = m;
		this.lastSelectionDeletion = lastSelectionDeletion;
		this.contentToRestore = deletedContent;
	}
	
	@Override
	public void restore() {
		Selection restorationPoint = getRestorationPoint();
		m.selectionner(restorationPoint);
		m.inserer(contentToRestore);
	}

	private Selection getRestorationPoint() {
		return new SelectionImpl(lastSelectionDeletion.getStartIndex(), 0);
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
	
	@Override
	public String toString() {
		return "Memento d'annulation de la commande supprimer."
		+ "A la selection : " + getRestorationPoint() 
		+ "\n on retablie l'ancien contenue coupé qui est " + contentToRestore;
	}
}
