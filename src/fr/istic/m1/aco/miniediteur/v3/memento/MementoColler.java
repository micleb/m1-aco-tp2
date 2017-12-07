package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

/**
 * Mémento d'annulation de la commande coller. On supprime le contenu collé et on restore le contenu précédement écrasé si la séléction était non vide.
 */
public class MementoColler implements Memento {

	private final String pastedBuffer;
	private final Selection pasteDestination;
	private final Moteur m;
	private final String contentToRestore;
	
	/**
	 * Constructeur.
	 * @param m Le moteur sur lequel a eu lieu l'execution de la commande.
	 * @param overwrittenContent Le contenu écrasé par la commande copier, dans le cas où la selection est non vide.
	 * @param pasteDestination La selection sur laquelle la commande supprimer à été effectuée.
	 * @precondition m != null & removedContent != null & pasteDestination != null.
	 * @precondition Si la commande coller n'a pas écrasé de contenu (cas de la sélection est vide) alors la overwrittenContent est une chaîne vide. Null est interdit.
	 */
	public MementoColler(Moteur m, String overwrittenContent, Selection pasteDestination) {
		
		if (m == null || overwrittenContent == null || pasteDestination == null) {
			throw new IllegalArgumentException("Null est interdit");
		}
		
		checkArgumentConsistency(overwrittenContent, pasteDestination);
		this.pastedBuffer = m.getPresspapierContent();
		this.pasteDestination = pasteDestination;
		this.m = m;
		this.contentToRestore = overwrittenContent; 
	}
	
	/**
	 * On vérifie la logique des arguments : Si par exemple on prétend que le contenu écrasé est non vide alors
	 * que la sélection sur laquelle la commande a été effectué est vide, ce n'est pas cohérence et révélateur d'un bug dans la classe cliente.
	 */
	private void checkArgumentConsistency(String overwrittenContent, Selection pasteDestination) {
		if (overwrittenContent.equals("") && !pasteDestination.isEmpty()) {
			throw new IllegalArgumentException("Arguments non cohérent : overwrittenContent est vide alors que la selection n'est pas vide.");
		}
		if (!overwrittenContent.equals("") && pasteDestination.isEmpty()) {
			throw new IllegalArgumentException("Arguments non cohérent : overwrittenContent n'est pas vide alors que la selection est vide.");
		}
		assert(!overwrittenContent.equals("") && !pasteDestination.isEmpty()) || (overwrittenContent.equals("") && pasteDestination.isEmpty());
	}
	@Override
	public void restore() {	
		Selection s = getSelectionResultante();
		m.selectionner(s);
		m.supprimer();
		Selection restoreDst = new SelectionImpl(pasteDestination.getStartIndex(),0);
		m.selectionner(restoreDst);
		m.inserer(contentToRestore);
		m.selectionner(pasteDestination);
	}
	
	@Override
	public void cancelRestore() {
		m.selectionner(pasteDestination);
		m.inserer(pastedBuffer);
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
		return "Memento d'annulation de la commande coller : \n"
				+ "On supprime le contenu coller " + pastedBuffer +
				"\n dans la sélection " + pasteDestination +
				"\n et on restore le contenu écrasé qui est : " + this.contentToRestore;
	}
}
