package fr.istic.m1.aco.miniediteur.v1.invoker;

import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public interface Controller {
	public String getText();
	public Selection getSelection();
	public String getLastInseredContent();
}
