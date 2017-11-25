package fr.istic.m1.aco.miniediteur.v1.memento;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.istic.m1.aco.miniediteur.v1.command.Command;


public class CommandsHistoric {
	private Deque<Command> historic;
	private Deque<Command> undoHistoric;
	
	public CommandsHistoric() {
		historic = new ArrayDeque<>();
		undoHistoric = new ArrayDeque<>();
	}
	
	public void registerCommand(Command cmd) {
		if (cmd.getMemento() == EmptyMemento.getUniqueInstance()) {
			return ;
		}
		
		//On garde une selection que si elle est suivi d'une commande qui effectue un changement.
		if (cmd.getMemento().isIntermediateMemento() && !historic.isEmpty() && historic.peek().getMemento().isIntermediateMemento()) {
			return ;
		}
		historic.push(cmd);
		System.out.println("Registration : " + cmd);
	}
	
	public void undo() {
		if (!historic.isEmpty()) {
			Command cmd = historic.pop();
			undoHistoric.push(cmd);
			
			System.out.println(cmd.getMemento());
			cmd.getMemento().restore();
			
			if (cmd.getMemento().isIntermediateMemento()){
				undo();
			}
		}
	}
	
	public void redo() {
		System.out.println("TAILLE REDO's is : " + undoHistoric.size());
		if (!undoHistoric.isEmpty()) {
			Command cmd = undoHistoric.pop();
			
			System.out.println("REDO ---> \n" + cmd);
			registerCommand(cmd);
			cmd.executer();
			
			if (cmd.getMemento().isIntermediateMemento()) {
				redo();
			}
		}
	}
}
