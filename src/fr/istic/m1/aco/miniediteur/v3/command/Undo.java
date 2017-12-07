package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v3.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;

/**
 * Commande d'annulation de commande (CTRL-Z).
 * Rétablie l'état précédant du texte de l'éditeur.
 * Si il n'y a rien à annuler, on envoie un message d'erreur à l'utilisateur.
 */
public class Undo implements Command {

	private final CommandsHistoric historic;
	private final IHM ui;
	
	public Undo(CommandsHistoric historic, IHM ui) {
		this.historic = historic;
		this.ui = ui;
	}

	@Override
	public void executer() {
		if (this.historic.commandsHistoricsSize() > 0) {
			this.historic.undo();
		}
		else {
			ui.notifyUser("Erreur: Rien à annuler");
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
