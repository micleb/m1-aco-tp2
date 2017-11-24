package fr.istic.m1.aco.miniediteur.v1.invoker;

public class Launcher {

	public static void main(String[] args) {
		IHM ihm = new ConsoleIHM();
		ControllerImpl ctlr = new ControllerImpl(ihm);

	}

}
