package fr.istic.m1.aco.miniediteur.v1.invoker;

import java.util.Scanner;

import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public class ConsoleIHM implements IHM {
    
	Scanner input;
	
	public ConsoleIHM() {
		input = new Scanner(System.in);
		start();
	}
	
	
	public void start() {
		String txt = input.next();
	}
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDisplayedContent(String s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getLastInsereredContent() {
		// TODO Auto-generated method stub
		return null;
	}	
}
