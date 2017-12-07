package fr.istic.m1.aco.miniediteur.v3.invoker;

import fr.istic.m1.aco.miniediteur.v3.command.CommandEnum;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

/**
 * Le contrôleur fait le lien entre les Implémentation de l'interface IHM et les commandes.
 * Les IHM devraient passer par un controlleur pour l'execution de chaque commande et ne pas chercher à les executer elles mêmes.
 */
public interface Controller {
	public void execute(CommandEnum cmd);
	public String getClipboard();
	public String getBuffer();
	public Selection getSelection();
	public String getSelectedContent();
}
