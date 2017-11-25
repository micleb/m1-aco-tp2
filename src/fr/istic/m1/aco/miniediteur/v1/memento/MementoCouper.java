package fr.istic.m1.aco.miniediteur.v1.memento;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class MementoCouper implements Memento {

	private final String cuttedContent;
	private final Selection cuttedSelection;
	private final Moteur m;
	
	public MementoCouper(Moteur m, Selection cuttedSelection, String cuttedContent) {
		if (m == null || cuttedContent == null || cuttedContent.equals("") || cuttedSelection.isEmpty()) {
			throw new IllegalArgumentException("Null parameters are not allowed"); 
		}
		this.cuttedContent = cuttedContent;
		this.cuttedSelection = cuttedSelection;
		this.m = m;
	}
	
	@Override
	public void restore() {
		Selection s = getSelectionRestoration();
		m.selectionner(s);
		m.inserer(cuttedContent);
		m.selectionner(cuttedSelection);
	}
	@Override
	public boolean isIntermediateMemento() {
		return false;
	}
	
	private Selection getSelectionRestoration() {
		return new SelectionImpl(cuttedSelection.getStartIndex(), 0);
	}
	
	@Override
	public String toString() {
		return "Memento d'annulation de la commande couper."
		+ "A la selection : " + getSelectionRestoration()
		+ " on retablie l'ancien contenue coupé qui est "
		+ " " + this.cuttedContent;
	}

}
