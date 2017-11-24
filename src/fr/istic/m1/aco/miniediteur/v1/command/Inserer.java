package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.invoker.Controller;
import fr.istic.m1.aco.miniediteur.v1.invoker.ControllerImpl;
import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.memento.MementoInserer;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;

public class Inserer implements Command {

	private Controller ctrl;
	private Moteur moteur;
	
	public Inserer(Moteur moteur,Controller ctrl) {
		this.ctrl = ctrl;
		this.moteur = moteur;
	}

	@Override
	public void executer() {
		String insertion = ctrl.getLastInseredContent();
		moteur.inserer(insertion);
	}

	@Override
	public Memento getMemento() {
		return new MementoInserer(moteur.getContent(), moteur.getCurrentSelection(), moteur);
	}
}
