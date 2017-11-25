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
	
	@Override
	public String getContentAt(Selection s) {
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
		
		if (selection.getLength() == 0) {
			this.content.insert(selection.getStartIndex(), content);
		}
		else {
			this.content.replace(selection.getStartIndex(), selection.getEndIndex()+1, content);
		}
		
	}
	
	@Override
	public void inserer(char c) {
		this.content.insert(selection.getStartIndex(), c);
	}
	@Override
	public void selectionner(Selection s) { //TODO BORNES
		
		if (s.getLength() > this.content.length() || s.getStartIndex() > this.content.length()  || s.getEndIndex() > this.content.length()) {
			String details = "Taille du contenu : " + this.content.length() + " Index de début : " + s.getStartIndex() + " Index de fin : " + s.getEndIndex();
			throw new IllegalArgumentException("Paramètres incorrectes dans sélectionner : bornes incorrectes : " + details);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((pressPapier == null) ? 0 : pressPapier.hashCode());
		result = prime * result + ((selection == null) ? 0 : selection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MoteurImpl other = (MoteurImpl) obj;
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (pressPapier == null) {
			if (other.pressPapier != null) {
				return false;
			}
		} else if (!pressPapier.equals(other.pressPapier)) {
			return false;
		}
		if (selection == null) {
			if (other.selection != null) {
				return false;
			}
		} else if (!selection.equals(other.selection)) {
			return false;
		}
		return true;
	}
}
