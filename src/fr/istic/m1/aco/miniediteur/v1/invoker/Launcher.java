package fr.istic.m1.aco.miniediteur.v1.invoker;

import fr.istic.m1.aco.miniediteur.v1.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v1.memento.CommandsHistoricImpl;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.MoteurImpl;

public class Launcher {

	public static void main(String[] args) {
		Moteur m = new MoteurImpl();
		CommandsHistoric historic = new CommandsHistoricImpl();
		Controller ctrl = new ControllerImpl(m, historic);
		IHM ihm = new MiniEditorTextInterface(ctrl);
		ihm.start();
	}
}
