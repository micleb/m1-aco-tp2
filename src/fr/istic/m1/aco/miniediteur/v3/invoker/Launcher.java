package fr.istic.m1.aco.miniediteur.v3.invoker;

import java.util.HashMap;
import java.util.Map;

import fr.istic.m1.aco.miniediteur.v3.command.*;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoric;
import fr.istic.m1.aco.miniediteur.v3.memento.CommandsHistoricImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Enregistreur;
import fr.istic.m1.aco.miniediteur.v3.receiver.EnregistreurImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;

public class Launcher {

	public static void main(String[] args) {
		Moteur m = new MoteurImpl();
		Enregistreur recorder = new EnregistreurImpl();
		CommandsHistoric historic = new CommandsHistoricImpl();
	
		Map<CommandEnum, Command> cmds = new HashMap<>();
		Controller ctrl = new ControllerImpl(m, cmds, recorder, historic);	
		IHM ihm = new MiniEditorTextInterface(ctrl);		
		cmds.put(CommandEnum.COPY, new Copier(m));
		cmds.put(CommandEnum.CUT, new Couper(m));
		cmds.put(CommandEnum.INSERT, new Inserer(m,ihm));
		cmds.put(CommandEnum.SELECT, new Selectionner(m,ihm));
		cmds.put(CommandEnum.PASTE, new Coller(m));
		cmds.put(CommandEnum.START_RECORDING, new StartRecording(recorder, ihm));	
		cmds.put(CommandEnum.STOP_RECORDING, new StopRecording(recorder, ihm));	
		cmds.put(CommandEnum.REPLAY, new Replay(recorder));	
		cmds.put(CommandEnum.UNDO, new Undo(historic, ihm));
		cmds.put(CommandEnum.REDO, new Redo(historic, ihm));
		
		ihm.start();
	}
}
