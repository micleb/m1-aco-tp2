package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;

/**
 * Commande pour stopper l'enregistrement de macro.
 * On affiche un message d'erreur à l'utilisateur en cas d'utilisation invalide,
 * comme tenter de stopper un enregistreur déjà stoppé. 
 * 
 */
public class StopRecording implements Command {

	private final Enregistreur rec;
	private final IHM ui;
	
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
