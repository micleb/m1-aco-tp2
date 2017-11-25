package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.memento.MementoColler;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;

public class Coller implements Command {

	private Moteur m;
	private Memento mem;
	
	public Coller(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer() {
		if (mem == null) {
			mem = getMemento();
		}
		m.coller();
	}
	
	@Override
	public Memento getMemento() {
		if (mem == null) {
			String removedContent = m.getSelectedContent();
			mem = new MementoColler(m, removedContent);
		}
		return mem;
	}
	
	@Override
	public String toString() {
		return "Commande coller dans la selection : " + m.getCurrentSelection() 
		+ " qui contient " + m.getSelectedContent() + " \n alors remplacé par le contenu du press-papier qui est :"
		+ " " + m.getPresspapierContent();
	}
}