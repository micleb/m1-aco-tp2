package fr.istic.m1.aco.miniediteur.v1.receiver;

public class MoteurImpl implements Moteur {
	private StringBuffer content;
	private PressPapier pressPapier;
	private Selection selection;
	
	public MoteurImpl() {
		this.content = new StringBuffer();
		this.pressPapier = new PressPapier();
	}
	
	/**
	 * On veut pouvoir tester les méthodes qui nécéssitent sans être dépendant 
	 * de la méthode sélectionner (pour faire des tests unitaires).
	 * Le test de la méthode copier (par exemple) ne doit pas dépendre des méthodes
	 * d'insertion de contenu.
	 * @param b
	 */
	public MoteurImpl(StringBuffer b) {
		this.content = b;
		this.selection = new SelectionImpl(0, 1);
		this.pressPapier = new PressPapier();
	}
	
	
	private String getContentAt(Selection s) {
		if (s.isEmpty()) {
			return "";
		}
		else {
			return this.content.substring(s.getStartIndex(), s.getEndIndex() + 1);
		}
	}
	
	@Override
	public String getSelectedContent() {
		return getContentAt(this.getCurrentSelection());
	}
	
	@Override
	public void couper() {
		this.copier();
		this.supprimer();
	}

	@Override
	public void coller() {
		int offset = selection.isEmpty()?0:1;
		this.content.replace(selection.getStartIndex(), selection.getEndIndex()+offset, this.pressPapier.getContent());
	}

	@Override
	public void copier() {
		String selectedText = this.content.substring(selection.getStartIndex(), selection.getEndIndex() + 1);
		this.pressPapier.setContent(selectedText);
	}
	
	@Override
	public void supprimer() {
		this.content.delete(selection.getStartIndex(), selection.getEndIndex() + 1);
	}

	@Override
	public void inserer(String content) {
		this.content.insert(selection.getStartIndex(), content);
	}
	
	@Override
	public void inserer(char c) {
		this.content.insert(selection.getStartIndex(), c);
	}

	@Override
	public void selectionner(Selection s) {
		if (s.getLength() > this.content.length() || s.getStartIndex() > this.content.length() - 1 || s.getEndIndex() > this.content.length() - 1) {
			System.out.println("len : " + this.content.length() + " endIdx : " + s.getEndIndex());
			throw new IllegalArgumentException("Paramètres incorrectes dans sélectionner : bornes incorrectes");
		}
		this.selection = s;
	}
	//TODO
	/*public void setPresspapierContent(String newContent) {
		if (newContent == null) {
			pressPapier.clear();
		}
		else {
			this.pressPapier.setContent(newContent);
		}
	}*/
	public String getPresspapierContent() {
		return this.pressPapier.getContent();
	}

	@Override
	public String getContent() {
		return content.toString();
	}

	@Override
	public Selection getCurrentSelection() {
		return selection;
	}
}
