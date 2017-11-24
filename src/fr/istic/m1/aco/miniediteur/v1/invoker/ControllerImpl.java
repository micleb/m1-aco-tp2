package fr.istic.m1.aco.miniediteur.v1.invoker;

import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public class ControllerImpl implements Controller{
	
	IHM ihm;
	
	public ControllerImpl(IHM ihm) {
		this.ihm = ihm;
	}
	
	public String getText() {
		return ihm.getText();
	}
	
	public Selection getSelection() {
		return ihm.getSelection();
	}

	public String getLastInseredContent() {
		return ihm.getLastInsereredContent();
	}
}
