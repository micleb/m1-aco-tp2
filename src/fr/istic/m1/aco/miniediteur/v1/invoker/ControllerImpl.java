package fr.istic.m1.aco.miniediteur.v1.invoker;

import fr.istic.m1.aco.miniediteur.v1.command.Coller;
import fr.istic.m1.aco.miniediteur.v1.command.Command;
import fr.istic.m1.aco.miniediteur.v1.command.Copier;
import fr.istic.m1.aco.miniediteur.v1.command.Couper;
import fr.istic.m1.aco.miniediteur.v1.command.Inserer;
import fr.istic.m1.aco.miniediteur.v1.command.Selectionner;
import fr.istic.m1.aco.miniediteur.v1.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v1.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v1.receiver.SelectionImpl;

public class ControllerImpl implements Controller{
	
	Moteur m;
	CommandsHistoric historic;
	
	public ControllerImpl(Moteur m, CommandsHistoric historic) {
		this.m = m;
		this.historic = historic;
	}
		
	@Override
	public Selection getSelection() {
		return m.getCurrentSelection();
	}

	@Override
	public String getBuffer() {
		return m.getContent();
	}
	
	@Override
	public String getClipboard() {
		return m.getPresspapierContent();
	}
	
	private void executeCommand(Command cmd) {
		historic.registerCommand(cmd);
		cmd.executer();
	}
	
	@Override
	public void select(int start, int stop) {
		Selection s = new SelectionImpl(start, stop-start);
		Command select = new Selectionner(m, s);
		executeCommand(select);
	}
	@Override
	public void copy() {
		Command copy = new Copier(m);
		executeCommand(copy);
	}
	@Override
	public void cut() {
		Command cut = new Couper(m);
		executeCommand(cut);
	}
	@Override
	public void paste() {
		Command paste = new Coller(m);
		executeCommand(paste);
	}
	@Override
	public void insert(String content) {
		Command insert = new Inserer(m, content);
		executeCommand(insert);
	}
	@Override
	public void delete() {
		System.out.println("Not Yet implemented");
	}
	
	@Override
	public void undo() {
		historic.undo();
	}
	@Override
	public void redo() {
		historic.redo();
	}

	@Override
	public String getSelectedContent() {
		return m.getSelectedContent();
	}
	
	
}
