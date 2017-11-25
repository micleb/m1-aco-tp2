package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.memento.MementoCouper;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;

public class Couper implements Command {

	Moteur m;
	Memento mem;
	String cuttedContent;
	
	public Couper(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer() {
		mem = getMemento();
		m.couper();
	}

	@Override
	public Memento getMemento() {
		if (mem == null) {
			cuttedContent = m.getSelectedContent();
			mem = new MementoCouper(m, m.getCurrentSelection(), cuttedContent); 
		}
		return mem;
	}
	
	@Override
	public String toString() {
		return "Commande couper de la selection : " + m.getCurrentSelection() 
		+ " qui contient " + m.getSelectedContent();
	}
}
