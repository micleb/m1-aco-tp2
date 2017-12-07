package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;

/**
 * Commande pour démarrer l'enregistrement de macro.
 * On affiche un message d'erreur à l'utilisateur en cas d'utilisation invalide,
 * comme tenter de démarrer un enregistreur déjà démarré. 
 * 
 */
public class StartRecording implements Command {
	private final Enregistreur rec;
	private final IHM ui;
	
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