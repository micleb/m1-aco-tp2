package fr.istic.m1.aco.miniediteur.v3.receiver;

import fr.istic.m1.aco.miniediteur.v3.command.Command;

/**
 * Interface abstraite pour l'enregistrement de macros.
 * Une fois démarré, on peut enregister des commande (via rajouter(Command cmd) que l'on peut ensuite rejouter d'un coup
 * via la méthode rejouer().  
 *
 */
public interface Enregistreur {
	/**
	 * Démarre l'enregistrement, maintenant l'enregisreur accepte des appels à rajouter(Commande cmd).
	 * @see Enregistreur#rajouter(Command)
	 * @precondition isOn() == false
	 * @postcondition isOn() == true
	 */
	public void demarrer();

	/**
	 * Stop l'enregistrement, maintenant l'enregisreur n'accepte plus d'appel à Enregistreur#rajouter(Command) .
	 * @precondition isOn() == true
	 * @postcondition isOn() == false
	 */
	public void stopper();
	
	/**
	 * @param cmd La commande suivante à ajouter à la macro.
	 * @postcondition isOn() == true
	 */
	public void rajouter(Command cmd);

	/**
	 * Rejoue les commandes précément enregistées via Enregistreur#rajouter(Command) . 
	 */
	public void rejouer();

	/**
	 * @return True si l'enregistreur est démarré, sinon false.
	 */
	public boolean isOn();
}
