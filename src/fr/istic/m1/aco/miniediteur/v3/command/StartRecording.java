package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;

public class StartRecording implements Command {
	private Enregistreur rec;
	private IHM ui;
	
	public StartRecording(Enregistreur recorder, IHM ui) {
		this.ui = ui;
		this.rec = recorder;
	}

	@Override
	public void executer() {
		if (rec.isOn()) {
			ui.notifyUser("Erreur : l'enregistrement est déjà démarré.");
		}
		else {
			ui.notifyUser("Démarrage de l'enregistrement.");
			rec.demarrer();
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