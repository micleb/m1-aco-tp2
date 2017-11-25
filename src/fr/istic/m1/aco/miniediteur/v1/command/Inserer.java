package fr.istic.m1.aco.miniediteur.v1.command;

import fr.istic.m1.aco.miniediteur.v1.memento.Memento;
import fr.istic.m1.aco.miniediteur.v1.memento.MementoInserer;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;

public class Inserer implements Command {

	private String content;
	private Moteur moteur;
	Memento mem;
	
	public Inserer(Moteur moteur, String content) {
		this.content = content;
		this.moteur = moteur;
	}
	
	@Override
	public void executer() {
		mem=getMemento();
		moteur.inserer(content);
	}

	@Override
	public Memento getMemento() {
		if (mem == null) { 
			String deletedContent = "";
			if (!moteur.getCurrentSelection().isEmpty()) {
				deletedContent = moteur.getSelectedContent();
			}
			mem = new MementoInserer(moteur.getCurrentSelection(), content.length(), deletedContent, moteur);	
		}
		return mem;
	}
	
	@Override
	public String toString() {
		return "Commande inserer sur la selection : " + moteur.getCurrentSelection() 
		+ " qui contient " + moteur.getSelectedContent() 
		+  " ecrasé par l'insertion de " + content;
	}
}
