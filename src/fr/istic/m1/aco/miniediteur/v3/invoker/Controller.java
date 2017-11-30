package fr.istic.m1.aco.miniediteur.v3.invoker;

import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

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

	String getSelectedContent();
	
	public void startRecording();
	public void stopRecording();
	public void replayRecorded();
}
