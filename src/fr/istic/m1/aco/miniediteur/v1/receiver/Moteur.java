package fr.istic.m1.aco.miniediteur.v1.receiver;

/**
 * @author bzherlb
 *
 */
public interface Moteur {
	
	/**
	 * 
	 */
	public void couper();
	
	/**
	 * 
	 */
	public void coller();
	
	/**
	 * @return 
	 * 
	 */
	public void copier();
	
	public void supprimer();
	
	/**
	 * 
	 * @param s
	 */
	public void inserer(String content);
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public void selectionner(Selection s);
	
	
	/**
	 * @return La selection actuelle, c'est à dire là où toute action sera executée.
	 */
	public Selection getCurrentSelection(); 
	
	public String getPresspapierContent();
	
	public String getContent();
	
	public void inserer(char c);

	public String getSelectedContent();
}