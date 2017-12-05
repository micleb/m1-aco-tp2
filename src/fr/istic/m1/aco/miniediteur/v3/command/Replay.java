package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;

public class Replay implements Command {

	private Enregistreur rec;
	
	public Replay(Enregistreur recorder) {
		this.rec = recorder;
	}
	
	@Override
	public void executer() {
		rec.rejouer();
	}

	@Override
	public Memento getMemento() {
		return EmptyMemento.getUniqueInstance();
	}

	@Override
	public ReplayableCommand asReplayableCommand() {
		return new EmptyReplayableCommand();
	}

}