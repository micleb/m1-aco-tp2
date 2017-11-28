package fr.istic.m1.aco.miniediteur.v1.memento;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.istic.m1.aco.miniediteur.v1.command.Command;


/**
 * @author bzherlb
 * Cette classe représente le stockage de l'historique des commandes effectuées 
 * et offre des opérations pour pouvoir les défaires et les refaires.
 * 
 * Il faut d'abord signaler à l'historique qu'une commande à été effectuée via la méthode registerCommand(),
 * puis effecter les opérations défaire,refaire respectivement par undo redo.
 */
public class CommandsHistoricImpl implements CommandsHistoric {
	private Deque<Command> historic;
	private Deque<Command> undoHistoric;
	
	public CommandsHistoricImpl() {
		historic = new ArrayDeque<>();
		undoHistoric = new ArrayDeque<>();
	}
	
	@Override
	public void registerCommand(Command cmd) {
		if (cmd.getMemento() == EmptyMemento.getUniqueInstance()) {
			return ;
		}
		
		//On garde une selection que si elle est suivi d'une commande qui effectue un changement.
		if (cmd.getMemento().isIntermediateMemento() && !historic.isEmpty() && historic.peek().getMemento().isIntermediateMemento()) {
			return ;
		}
		historic.push(cmd);
		//System.out.println("Registration : " + cmd);
	}
	
	/**
	 * Annule la dernière commande enregistrée en restorant
	 * le contenu du moteur à son état avant execution.
	 * 
	 * Cette commande peut-alors être rejouée via la méthode redo().   
	 */
	@Override
	public void undo() {
		if (!historic.isEmpty()) {
			Command cmd = historic.pop();
			undoHistoric.push(cmd);
			
			//System.out.println(cmd.getMemento());
			cmd.getMemento().restore();
			
			if (cmd.getMemento().isIntermediateMemento()){
				undo();
			}
		}
	}
	
	/**
	 * Restore la dernière commande précedement annulée en la rejouant.
	 * Il est possible de l'annuler de nouveau via un nouvel appel à undo().
	 */
	@Override
	public void redo() {
		//System.out.println("TAILLE REDO's is : " + undoHistoric.size());
		if (!undoHistoric.isEmpty()) {
			Command cmd = undoHistoric.pop();
			
			//System.out.println("REDO ---> \n" + cmd);
			registerCommand(cmd);
			cmd.executer();
			
			if (cmd.getMemento().isIntermediateMemento()) {
				redo();
			}
		}
	}
}
