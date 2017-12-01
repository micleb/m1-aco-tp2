package fr.istic.m1.aco.miniediteur.v3.memento;

import fr.istic.m1.aco.miniediteur.v3.command.Command;

public interface CommandsHistoric {

	void registerCommand(Command cmd);

	void undo();

	void redo();

}
