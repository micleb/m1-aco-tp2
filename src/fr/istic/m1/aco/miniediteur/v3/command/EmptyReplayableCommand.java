package fr.istic.m1.aco.miniediteur.v3.command;

/**
 * Implémentation du patterne "objet spécial" pour une commande rejouable vide. 
 * Utilisé lorsqu'une commande n'a pas de sens d'être rejouée (par exemple on ne rejoue pas le démarrage de l'enregistreur) 
 * une commande qui implement l'interface Command peuvent utiliser cette commande rejouable vide.
 * 
 * Cela permet de garder l'interface commande générique tout en évitant l'utilisation de null.
 */
public class EmptyReplayableCommand implements ReplayableCommand {

	@Override
	public void executer() {
		
	}
}
