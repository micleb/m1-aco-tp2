package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;

/**
 * Commande pour rejouer une commande annulée (CTRL-Y).
 * Rétablie l'état avant annulation du texte de l'éditeur.
 * Si il n'y a rien à restorer, on envoie un message d'erreur à l'utilisateur.
 */
public class Redo implements Command {

	private final CommandsHistoric historic;
	private final IHM ui;
	
	public Redo(CommandsHistoric historic, IHM ui) {
		this.historic = historic;
		this.ui = ui;
	}

	@Override
	public void executer() {
		if (historic.cancelationHistoricSize() > 0) {
			this.historic.redo();
		}
		else {
			ui.notifyUser("Erreur : Il n'y a pas de commande précédement annulée, donc rien à refaire");
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