package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class ReplayableInserer implements ReplayableCommand {

	private final String insertedContent;
	private final Selection insertion;
	private final Moteur m;
	
	public ReplayableInserer(Selection insertion, String insertedContent, Moteur m) {
		this.insertedContent = insertedContent;
		this.insertion = insertion;
		this.m = m;
	}
	
	@Override
	public void executer() {
		m.selectionner(insertion);
		m.inserer(insertedContent);
	}	
}
