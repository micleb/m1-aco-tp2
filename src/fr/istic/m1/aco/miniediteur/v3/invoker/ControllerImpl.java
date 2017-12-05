package fr.istic.m1.aco.miniediteur.v3.invoker;

import java.util.Map;

import fr.istic.m1.aco.miniediteur.v3.command.Command;
import fr.istic.m1.aco.miniediteur.v3.command.CommandEnum;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;
import fr.istic.m1.aco.miniediteur.v3.receiver.EnregistreurImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;

public class ControllerImpl implements Controller{
	
	private Moteur m;
	private CommandsHistoric historic;
	private Enregistreur recorder;
	
	private Map<CommandEnum, Command> cmdRetriever;
	
	public ControllerImpl(Moteur m, Map<CommandEnum, Command> cmdsMap, Enregistreur recorder, CommandsHistoric historic) {
		this.m = m;
		this.historic = historic;
		this.recorder = recorder;
		recorder = new EnregistreurImpl();
		
		this.cmdRetriever = cmdsMap;
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
	
	@Override
	public void execute(CommandEnum command) {
		Command cmd = this.cmdRetriever.get(command);
		cmd.executer();
		historic.registerCommand(cmd);
		if (recorder.isOn()) {
			recorder.rajouter(cmd);
		}		
	}

	@Override
	public String getSelectedContent() {
		return m.getSelectedContent();
	}
}
