package fr.istic.m1.aco.miniediteur.v1.memento;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public class MementoCouper implements Memento {

	private final String buffer;
	private final Selection cuttedSelection;
	private final Moteur m;
	
	public MementoCouper(Moteur m) {
		this.buffer = m.getPresspapierContent();
		this.cuttedSelection = m.getCurrentSelection();
		this.m = m;
	}
	
	@Override
	public void restore() {
		m.selectionner(cuttedSelection);
		m.inserer(buffer);
	}

}
