package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoCouper;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class Couper implements Command, ReplayableCommand {

	Moteur m;
	Selection lastSelection;
	
	public Couper(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer() {
		lastSelection = m.getCurrentSelection();
		m.couper();
	}

	@Override
	public Memento getMemento() {
		return new MementoCouper(m, lastSelection, m.getPresspapierContent());	
	}
	
	@Override
	public String toString() {
		return "Commande couper de la selection : " + m.getCurrentSelection() 
		+ " qui contient " + m.getSelectedContent();
	}

	@Override
	public ReplayableCommand asReplayableCommand() {
		return this;
	}
}
