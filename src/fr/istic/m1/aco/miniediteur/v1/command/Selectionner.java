package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.invoker.ControllerImpl;
import fr.istic.m1.aco.miniediteur.v1.memento.EmptyMemento;
import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public class Selectionner implements Command {

	private ControllerImpl ctrl;
	private Moteur moteur;
	
	public Selectionner(ControllerImpl ctrl, Moteur moteur) {
		this.ctrl = ctrl;
		this.moteur = moteur;
	}

	@Override
	public void executer() {
		Selection selection = ctrl.getSelection();
		moteur.selectionner(selection);	
	}

	@Override
	public Memento getMemento() {
		return EmptyMemento.getUniqueInstance();
	}
}
