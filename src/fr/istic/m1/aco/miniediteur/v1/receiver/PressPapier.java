package fr.istic.m1.aco.miniediteur.v1.receiver;

/**
 * @author bzherlb
 * Représente le contenu du press papier, qui doit être affecté lors de l'utilisation de commandes
 * telle que copier, couper...
 */
class PressPapier {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buffer == null) ? 0 : buffer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PressPapier other = (PressPapier) obj;
		if (buffer == null) {
			if (other.buffer != null)
				return false;
		} else if (!buffer.equals(other.buffer))
			return false;
		return true;
	}
}