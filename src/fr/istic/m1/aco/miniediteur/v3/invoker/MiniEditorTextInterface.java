package fr.istic.m1.aco.miniediteur.v3.invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import fr.istic.m1.aco.miniediteur.v3.command.CommandEnum;

public class MiniEditorTextInterface implements IHM {
	private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	private final Controller ctrl;
	
	public MiniEditorTextInterface(Controller ctrl){
		if (ctrl == null) {
			throw new IllegalArgumentException("Null interdit");
		}
		this.ctrl = ctrl;
	}
	
	@Override
	public void start() {		
		String inputLine;
		char commandLetter;

		System.out.println("Welcome to MiniEditor V1.0") ;
		System.out.println("-----------------------------------------------------------") ;

		System.out.println("[I]nsert | [S]elect | [C]opy | X:cut | V:paste | [D]elete | start [R]ecording | [E]nd recording | [P]lay recording | Z:undo | Y:redo | [Q]uit > ") ;
		try
		{
			inputLine = keyboard.readLine();
		} catch (IOException e)
		{
			System.out.println("Unable to read standard input");
			inputLine = "W";
		}
		if (inputLine.isEmpty())
		{
			commandLetter = '0';
		}
		else
		{
			commandLetter = Character.toUpperCase(inputLine.charAt(0)) ;
		}
		while (commandLetter != 'Q') /* Quit */
		{
			switch (commandLetter)
			{
				case '0': break ;
				case 'I': /* Insert */
					ctrl.execute(CommandEnum.INSERT);
					break;
				case 'S': /* Select */
					ctrl.execute(CommandEnum.SELECT);
					break;
				case 'C': /* Copy */
					ctrl.execute(CommandEnum.COPY);
					break;
				case 'X': /* cut */
					ctrl.execute(CommandEnum.CUT);
					break;
				case 'V': /* paste */
					ctrl.execute(CommandEnum.PASTE);
					break;
				case 'D': /* Delete, i.e. insert empty string */
					ctrl.execute(CommandEnum.DELETE);
					break;
				case 'R': /* start Recording */
					// Insert your code here (V2)
					ctrl.execute(CommandEnum.START_RECORDING);
					break;
				case 'E': /* End recording */
					// Insert your code here (V2)
					ctrl.execute(CommandEnum.STOP_RECORDING);
					break;
				case 'P': /* Play recording */
					// Insert your code here (V2)
					ctrl.execute(CommandEnum.REPLAY);
					break;
				case 'Z': /* undo */
					// Insert your code here (V3)
					ctrl.execute(CommandEnum.UNDO);
					break;
				case 'Y': /* redo */
					// Insert your code here (V3)
					ctrl.execute(CommandEnum.REDO);
					break;
				default: System.out.println("Unrecognized command, please try again:") ;
					break;
			}
			System.out.println("-----------------------------------------------------");
			System.out.println("Contenu \n : [" + ctrl.getBuffer() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + ctrl.getSelection() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("Contenu de la selection \n : [" + ctrl.getSelectedContent() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("Presse-papier : [" + ctrl.getClipboard() + "]");
			System.out.println("=====================================================");

			System.out.println("[I]nsert | [S]elect | [C]opy | X:cut | V:paste | [D]elete | start [R]ecording | [E]nd recording | [P]lay recording | Z:undo | Y:redo | [Q]uit > ") ;
			try
			{
				inputLine = keyboard.readLine();
			} catch (IOException e)
			{
				System.out.println("Unable to read standard input");
				inputLine = "W";
			}
			if (inputLine.isEmpty())
			{
				commandLetter = '0';
			}
			else
			{
				commandLetter = Character.toUpperCase(inputLine.charAt(0)) ;
			}
		}
		System.out.println ("Goodbye") ;
	}
	
	private String readLine() {
		String inputLine;
		try{
			inputLine = keyboard.readLine();
		} 
		catch (IOException e){
			System.out.println("Unable to read standard input");
			inputLine = "W";
		}
		return inputLine;
	}
	
	@Override
	public int requestInt(String prompt) {
		System.out.println(prompt);
		String numberString="";
		int res = 0;
		try {
			numberString = keyboard.readLine();
			res = Integer.parseInt(numberString);			
		}
		catch (Exception e)
		{
			System.out.println("Invalid number: " + numberString);
		}
		return res;
	}
	
	@Override
	public String requestString(String prompt) {
		System.out.println(prompt);
		return readLine();
	}

	@Override
	public void notifyUser(String message) {
		System.out.println(message);
	}
}
