package fr.istic.m1.aco.miniediteur.v3.memento;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.istic.m1.aco.miniediteur.v3.command.Command;

/**
 * Une implementations possible de CommandsHistoric.
 * Cette implementation est basée sur un système de pile LIFO. 
 * On maintient deux piles, une pile pour mémorisé les commandes joués, une autre pour mémorisé les commandes annulées.
 * 
 * Lors qu'on annule une commande joués, on la place dont la la pile des commandes annulées pour pouvoir potentiellement restorer.
 */
public class CommandsHistoricImpl implements CommandsHistoric {
	private Deque<Memento> historic;
	private Deque<Memento> undoHistoric;

	public CommandsHistoricImpl() {
		historic = new ArrayDeque<>();
		undoHistoric = new ArrayDeque<>();
	}

	@Override
	public void registerCommand(Command cmd) {
		Memento mem = cmd.getMemento();
		registerMemento(mem);
		//System.out.println("Registration : " + cmd);
	}

	private void registerMemento(Memento mem) {
		if (mem == EmptyMemento.getUniqueInstance()) {
			return ;
		}

		//On ne garde une selection que si elle est suivi d'une commande qui effectue un changement.
		if (mem.isIntermediateMemento() && !historic.isEmpty() && historic.peek().isIntermediateMemento()) {
			return ;
		}
		historic.push(mem);
	}

	@Override
	public void undo() {
		if (this.historic.isEmpty()) {
			throw new IllegalStateException("Il n'y a pas de commande à annuler.");
		}
		Memento mem = historic.pop();
		undoHistoric.push(mem);

		//System.out.println(mem);
		mem.restore();
		
		//Pour avoir un comportement d'annulation naturel.
		//Dans la plupart des éditeurs, si l'on fait un couper puis une nouvelle sélection juste après, 
		//on s'attend à ce qu'un CTRL-Z annule immédiatement le couper, sans avoir à faire deux CTRL-Z d'affilé à cause de 
		//la selection faite après.
		if (mem.isIntermediateMemento()){
			undo();
		}
	}

	@Override
	public void redo() {
		if (undoHistoric.isEmpty()) {
			throw new IllegalStateException("Il n'y a pas de commande à rejouer.");
		}
		
		if (!undoHistoric.isEmpty()) {
			Memento cmd = undoHistoric.pop();

			System.out.println("REDO ---> \n" + cmd);
			registerMemento(cmd);
			cmd.cancelRestore();

			if (cmd.isIntermediateMemento()) {
				redo();
			}
		}
	}

	@Override
	public int commandsHistoricsSize() {
		return this.historic.size();
	}

	@Override
	public int cancelationHistoricSize() {
		return this.undoHistoric.size();
	}
}
