package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.memento.MementoColler;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;

public class Coller implements Command {

	private Moteur m;
	private String removedContent;
	private Memento mem;
	
	public Coller(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer() {
		this.removedContent = m.getSelectedContent();
		mem = new MementoColler(m, removedContent);
		m.coller();
	}

	@Override
	public Memento getMemento() {
		return mem;
	}
}
