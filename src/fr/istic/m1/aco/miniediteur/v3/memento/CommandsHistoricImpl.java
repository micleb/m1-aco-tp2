package fr.istic.m1.aco.miniediteur.v3.memento;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.istic.m1.aco.miniediteur.v3.command.Command;

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

		//On garde une selection que si elle est suivi d'une commande qui effectue un changement.
		if (mem.isIntermediateMemento() && !historic.isEmpty() && historic.peek().isIntermediateMemento()) {
			return ;
		}
		historic.push(mem);
	}
	/**
	 * Annule la dernière commande enregistrée en restorant
	 * le contenu du moteur à son état avant execution.
	 * 
	 * Cette commande peut-alors être rejouée via la méthode redo().   
	 */
	@Override
	public void undo() {
		if (this.historic.isEmpty()) {
			throw new IllegalStateException("Il n'y a pas de commande à annuler.");
		}
		Memento mem = historic.pop();
		undoHistoric.push(mem);

		//System.out.println(mem);
		mem.restore();

		if (mem.isIntermediateMemento()){
			undo();
		}
	}

	/**
	 * Restore la dernière commande précedement annulée en la rejouant.
	 * Il est possible de l'annuler de nouveau via un nouvel appel à undo().
	 */
	@Override
	public void redo() {
		//System.out.println("TAILLE REDO's is : " + undoHistoric.size());
		if (undoHistoric.isEmpty()) {
			throw new IllegalStateException("Il n'y a pas de commande à rejouer.");
		}
		
		if (!undoHistoric.isEmpty()) {
			Memento cmd = undoHistoric.pop();

			//System.out.println("REDO ---> \n" + cmd);
			registerMemento(cmd);
			cmd.cancelRestore();;

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
