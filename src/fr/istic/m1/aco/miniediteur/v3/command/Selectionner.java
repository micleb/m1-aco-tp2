package fr.istic.m1.aco.miniediteur.v3.command;

import fr.istic.m1.aco.miniediteur.v3.invoker.IHM;
import fr.istic.m1.aco.miniediteur.v3.memento.Memento;
import fr.istic.m1.aco.miniediteur.v3.memento.MementoSelectionner;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

/**
 * Command de selection.
 * Puisque la commande executer ne prend pas de paramètres, 
 * On fait de l'inversion de resposabilité pour récupérer le choix de l'utilisateur quand à la sélection.
 * on utilise pour ça les méthodes de requête d'entier fournis par l'interface abstraite IHM.
 * 
 * Cette commande vérifie la validité de la sélection et envoie un message d'erreur à l'utilisateur 
 * en cas de sélection invalide. 
 */
public class Selectionner implements Command {

	private final Moteur moteur;
	private final IHM ui;
	
	public Selectionner(Moteur moteur, IHM ui) {
		this.moteur = moteur;
		this.ui = ui;
	}

	@Override
	public void executer() {
		int start = ui.requestInt("Début de la sélection ?");
		int stop = ui.requestInt("Taille de la sélection ?");
		Selection s = new SelectionImpl(start, stop);
		
		if (moteur.isValidSelection(s)) {
			moteur.selectionner(s);	
		}
		else {
			ui.notifyUser("Selection invalide, en dehors des bornes autorisées");
		}
		
	}

	@Override
	public Memento getMemento() {
		return new MementoSelectionner(moteur, moteur.getCurrentSelection());
	}
	
	public String toString() {
		return "Commande selection";
	}
	
	@Override
	public ReplayableCommand asReplayableCommand() {
		return new ReplayableSelectionner(moteur.getCurrentSelection(), moteur);
	}
}
