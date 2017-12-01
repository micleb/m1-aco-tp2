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
	
	void demarrer();
	
	void stoper();
	
	void rejouer();

	String getClipboard();

	String getBuffer();

	Selection getSelection();

	String getSelectedContent();
	
}
