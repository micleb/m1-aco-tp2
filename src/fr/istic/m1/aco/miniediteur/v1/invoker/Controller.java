package fr.istic.m1.aco.miniediteur.v1.invoker;

import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public interface Controller {

	void redo();

	void undo();

	void delete();

	void insert(String content);

	void paste();

	void cut();

	void copy();

	void select(int start, int stop);

	String getClipboard();

	String getBuffer();

	Selection getSelection();
	
}
