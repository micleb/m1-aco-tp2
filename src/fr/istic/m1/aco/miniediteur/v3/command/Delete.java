package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoSupprimer;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

public class Delete implements Command{
	Moteur m;
	Memento mem;
	String delete;
	
	public Delete(Moteur m) {
		this.delete = "";
		this.m = m;
				
	}
	
	public void executer() {
		mem = getMemento();
		m.supprimer();
	}

	@Override
	public Memento getMemento() {
		if (mem == null) {
			delete = m.getSelectedContent();
			Selection sel = new SelectionImpl(m.getCurrentSelection().getStartIndex(), 0);
			mem = new MementoSupprimer(m, sel, delete); 
		}
		return mem;
	}

	public String toString() {
		return "Commande delete";
	}
}
