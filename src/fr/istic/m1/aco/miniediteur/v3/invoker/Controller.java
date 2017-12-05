package fr.istic.m1.aco.miniediteur.v3.invoker;

import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.CommandEnum;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public interface Controller {

	public void execute(CommandEnum cmd);
	public String getClipboard();

	public String getBuffer();

	public Selection getSelection();

	public String getSelectedContent();
}
