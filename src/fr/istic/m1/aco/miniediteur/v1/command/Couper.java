package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.memento.MementoCouper;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;

public class Couper implements Command {

	Moteur m;
	
	public Couper(Moteur m) {
		this.m = m;
	}

	@Override
	public void executer() {
		m.couper();
	}

	@Override
	public Memento getMemento() {
		return new MementoCouper(m);
	}
}
