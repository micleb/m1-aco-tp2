package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

/**
 * Mémento d'annulation de la commande inserer. On supprime le contenu inseré et on restore le contenu précédement écrasé si la séléction était non vide.
 *
 */
public class MementoInserer implements Memento {

	private final int insertionLenght;
	private final String inseredContent;
	private final String overwrittenContent;
	private final Selection insertion;
	private final Moteur m;
		
	/**
	 * Constructeur.
	 * @param m Le moteur sur lequel a eu lieu l'execution de la commande.
	 * @param insertedContent Le contenu ajouté lors de l'execution de la commande. 
	 * @param overwrittenContent Le contenu écrasé par la commande inserer, dans le cas où la selection est non vide.
	 * @param insertionDestination La selection sur laquelle la commande supprimer à été effectuée.
	 * @precondition m {@literal !=} null {@literal &} removedContent {@literal !=} null {@literal &} insertionDestination {@literal !=} null \n.
	 * @precondition Si la commande inserer n'a pas écrasé de contenu (cas de la sélection est vide) alors overwrittenContent est une chaîne vide. Null est interdit.
	 */
	public MementoInserer(Selection insertionDestination, String insertedContent, String overwrittenContent, Moteur m) {
		
		if (m == null || overwrittenContent == null || insertionDestination == null) {
			throw new IllegalArgumentException("Null est interdit");
		}
		
		checkArgumentConsistency(overwrittenContent, insertionDestination);
		this.inseredContent = insertedContent;
		this.insertionLenght = insertedContent.length();
		this.overwrittenContent = overwrittenContent;
		this.insertion = insertionDestination;
		this.m = m;
	}

	/**
	 * On vérifie la logique des arguments : Si par exemple on prétend que le contenu écrasé est non vide alors
	 * que la sélection sur laquelle la commande a été effectué est vide, ce n'est pas cohérence et révélateur d'un bug dans la classe cliente.
	 */
	private void checkArgumentConsistency(String overwrittenContent, Selection insertionDestination) {
		if (overwrittenContent.equals("") && !insertionDestination.isEmpty()) {
			throw new IllegalArgumentException("Arguments non cohérent : overwrittenContent est vide alors que la selection n'est pas vide.");
		}
		if (!overwrittenContent.equals("") && insertionDestination.isEmpty()) {
			throw new IllegalArgumentException("Arguments non cohérent : overwrittenContent n'est pas vide alors que la selection est vide.");
		}
		assert(!overwrittenContent.equals("") && !insertionDestination.isEmpty()) || (overwrittenContent.equals("") && insertionDestination.isEmpty());
	}
	
	@Override
	public void restore() {
		m.selectionner(getSelectionOfNewContent());
		m.inserer(overwrittenContent);
		m.selectionner(insertion);
	}
	
	@Override
	public void cancelRestore() {
		m.selectionner(insertion);
		m.inserer(inseredContent);
	}
	
	private Selection getSelectionOfNewContent() {
		return new SelectionImpl(insertion.getStartIndex(), insertionLenght);
	}
	
	@Override
	public boolean isIntermediateMemento() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Memento d'annulation de la commande inserer."
		+ "A la selection : " + getSelectionOfNewContent()
		+ "\n on supprime le contenue inseré qui est " + this.inseredContent 
		+ "\n et on restore le contenu écrasé qui est : " + this.overwrittenContent;
	}
}
