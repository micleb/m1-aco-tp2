package fr.istic.m1.aco.miniediteur.v1.memento;

import fr.istic.m1.aco.miniediteur.v1.command.Command;

public interface CommandsHistoric {

	void registerCommand(Command cmd);

	void undo();

	void redo();

}
