package fr.istic.m1.aco.miniediteur.v1.invoker;

import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public interface IHM {
	public String getText();
	public Selection getSelection();
	public void clear();
	public void setDisplayedContent(String s);
	public String getLastInsereredContent();
}
