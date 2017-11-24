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
		historic.push(cmd);
	}
	
	public void undo() {
		if (!historic.isEmpty()) {
			Command cmd = historic.pop();
			cmd.getMemento().restore();
			undoHistoric.add(cmd);
		}
	}
	
	public void redo() {
		if (!undoHistoric.isEmpty()) {
			undoHistoric.pop().executer();
		}
	}
}
