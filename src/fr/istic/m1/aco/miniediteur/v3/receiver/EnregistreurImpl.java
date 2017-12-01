package fr.istic.m1.aco.miniediteur.v3.receiver;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.istic.m1.aco.miniediteur.v3.command.Command;

public class EnregistreurImpl implements Enregistreur {

	private Deque<Command> historic;
	private boolean isStarted;
	
	
	public EnregistreurImpl() {
		this.historic = new ArrayDeque<>();
		this.isStarted = false;
	}

	@Override
	public void demarrer() {
		if (!isStarted) {
			historic.clear();
			isStarted = true;
		}
		else {
			throw new IllegalStateException("L'enregisteur est déjà démarré");
		}
	}

	@Override
	public void stoper() {
		if (isStarted) {
			isStarted = false;
		}
		else {
			throw new IllegalStateException("L'enregisteur est déjà stopé");
		}
	}

	@Override
	public void rajouter(Command cmd) {
		historic.addLast(cmd);
	}
	
	@Override
	public void rejouer() {
		while (!historic.isEmpty()) {
			System.out.println(historic.peek().toString());
			historic.pop().executer();
		}
	}
}
