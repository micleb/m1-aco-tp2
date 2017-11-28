package fr.istic.m1.aco.miniediteur.v1.invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiniEditorTextInterface implements IHM {
	static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	Controller ctrl;

	public MiniEditorTextInterface(Controller ctrl) {
		this.ctrl = ctrl;
	}
	
	@Override
	public void start() {
		String inputLine;
		char commandLetter;

		System.out.println("Welcome to MiniEditor V1.0") ;
		System.out.println("-----------------------------------------------------------") ;

		System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Z/Y/Q) > ") ;
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
					ctrl.insert(this.readLine());
					break;
				case 'S': /* Select */
					String numberString="";
					try
					{
						String[] arguments = inputLine.substring(2).split("\\s+");
						numberString = arguments[0];
						int start  = Integer.parseInt(numberString);
						numberString = arguments[1];
						int stop  = Integer.parseInt(numberString);
						//editorEngine.editorSelect(start, stop);
						ctrl.select(start, stop);
					}
					catch (Exception e)
					{
						System.out.println("Invalid number: " + numberString);
					}
					break;
				case 'C': /* Copy */
					ctrl.copy();
					break;
				case 'X': /* cut */
					ctrl.cut();
					break;
				case 'V': /* paste */
					ctrl.paste();
					break;
				case 'D': /* Delete, i.e. insert empty string */
					ctrl.delete();
					break;
				case 'R': /* start Recording */
					// Insert your code here (V2)
					break;
				case 'E': /* End recording */
					// Insert your code here (V2)
					break;
				case 'P': /* Play recording */
					// Insert your code here (V2)
					break;
				case 'Z': /* undo */
					// Insert your code here (V3)
					ctrl.undo();
				case 'Y': /* redo */
					// Insert your code here (V3)
					ctrl.redo();
					break;
				default: System.out.println("Unrecognized command, please try again:") ;
					break;
			}
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + ctrl.getBuffer() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + ctrl.getSelection() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + ctrl.getClipboard() + "]");
			System.out.println("-----------------------------------------------------");

			System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Z/Y/Q) > ") ;
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
}
