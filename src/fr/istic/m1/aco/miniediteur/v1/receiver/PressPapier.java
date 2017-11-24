package fr.istic.m1.aco.miniediteur.v1.receiver;

public class PressPapier {
	private String buffer;
	
	
	PressPapier(){
		buffer = "";
	}
		
	public boolean isEmpty() {
		return buffer.length() == 0;
	}
	
	void setContent(String content) {
		if (content == null) {
			throw new IllegalArgumentException("Null interdit. Utilisez une chaîne vide pour representer un presse papier vide.");
		}
		buffer = content;
	}

	public String getContent() {
		return buffer;
	}
	
	public void clear() {
		this.buffer = "";
	}
}