package fr.istic.m1.aco.miniediteur.v3.receiver;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.ReplayableCommand;

public class EnregistreurImpl implements Enregistreur {

	private Deque<ReplayableCommand> historic;
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
	public void stopper() {
		if (isStarted) {
			isStarted = false;
		}
		else {
			throw new IllegalStateException("L'enregisteur est déjà stoppé.");
		}
	}

	@Override
	public void rajouter(Command cmd) {
		if (!isStarted) {
			throw new IllegalStateException("L'enregisteur n'est pas démarré.");
		}
		historic.addLast(cmd.asReplayableCommand());
	}
	
	@Override
	public void rejouer() {
		while (!historic.isEmpty()) {
			historic.pop().executer();
		}
	}
	
	@Override
	public boolean isOn() {
		return isStarted;
	}
}
