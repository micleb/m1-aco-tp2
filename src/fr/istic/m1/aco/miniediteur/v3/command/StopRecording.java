package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;

public class StopRecording implements Command {

	private Enregistreur rec;
	private IHM ui;
	
	public StopRecording(Enregistreur recorder, IHM ihm) {
		this.rec = recorder;
		this.ui = ihm;
	}

	@Override
	public void executer() {
		if (rec.isOn()) {
			rec.stopper();
		}
		else {
			ui.notifyUser("Erreur : l'enregistrement est déjà stoppé.");
		}
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
