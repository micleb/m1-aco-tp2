package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class ReplayableSelectionner implements ReplayableCommand {

	private final Selection s;
	private final Moteur m;
	
	public ReplayableSelectionner(Selection s, Moteur m) {
		this.s = s; 
		this.m = m;
	}
	
	@Override
	public void executer() {
		m.selectionner(s);
	}
}
