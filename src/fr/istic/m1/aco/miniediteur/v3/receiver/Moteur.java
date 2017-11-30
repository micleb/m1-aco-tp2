package fr.istic.m1.aco.miniediteur.v3.receiver;

/**
 * @author bzherlb
 * Le moteur représente l'état de notre éditeur et fournis une abstraction pour affecter cet état et le contenu édité.
 * L'état du moteur est représenté par le contenu du document édité, le contenu du presse-papier ainsi que la selection actuelle.
 * 
 * Les commandes affectants le contenu du document agissent sur la sélection actuelle.
 * 
 * Pour effectuer une commande qui agit sur le contenu, il faut donc d'abord effectuer une selection via la méthode selectionner.
 * 
 */
public interface Moteur {
	
	//TODO définir le cas où la selection est non vide : Ne rien faire? Lever une exception?
	/**
	 * Affecte l'état du contenu du document de ce moteur.
	 * L'état du presse papier devient le contenu de la sélection actuelle.
	 * Notez que l'ancien contenu du pressPapier est alors écrasé.
	 * 
	 * Le contenu sélectionné est supprimé du contenu du document.
	 * 
	 * Si la sélection est vide, 
	 * 
	 */
	public void couper();
	
	/**
	 * Affecte l'état du contenu du document de ce moteur.
	 * Ajoute le contenu du presse papier à la sélection actuelle.
	 * Si la sélection est vide, on n'écrase pas de contenu. 
	 * Sinon on écrase le contenu séléctionné.
	 */
	public void coller();
	
	/**
	 * Affecte l'état du presse papier de ce moteur. 
	 * Le presse papier contient maintenant le contenu de la selection actuelle. 
	 * Notez que l'ancien contenu du pressPapier est alors écrasé.
	 */
	public void copier();
	
	/**
	 * Affecte l'état du contenu du document de ce moteur.
	 * Supprime du contenu du document le contenu de la selection.
	 */
	public void supprimer();
	
	/**
	 * Affecte l'état du contenu du document de ce moteur.
	 * Si la sélection actuelle est vide, on ajoute le contenu donné à la suite de l'indice de départ de cette sélection,
	 * mais sans écraser de contenu.
	 * Si la sélection n'est pas vide, on insère en écrasant le contenu actuellement sélectionné.
	 * @param s Le contenu a ajouter.
	 */
	public void inserer(String content);
	
	/**
	 * Effectue une sélection, en vue d'effectuer une commande dessus.
	 * 
	 * Tout commande effectuée après l'appel de cette méthode agira sur cette selection.
	 * Si plusieurs selections ont lieu à suivre, c'est la dernière qui est effective. 
	 * 
	 * Une selection peut être vide si on ne veut pas qu'une commande tel que coller n'écrase de contenu.
	 * @param s La partie du texte à sélectionner.
	 * @throws IllegalStateException Si la selection n'est pas valide. 
	 * typiquement si l'indice de fin de la selection est supérieur à l'indice de fin du contenu.
	 */
	public void selectionner(Selection s);
	
	
	/**
	 * @return La selection actuelle, c'est à dire là où toute action sera executée.
	 */
	public Selection getCurrentSelection(); 
	
	/**
	 * @post getPresspapierContent() != null
	 * @return L'état actuelle du presse papier, éventuellement une chaine vide.
	 */
	public String getPresspapierContent();
	
	/**
	 * @return L'intégralité du contenu actuel de l'éditeur.
	 */
	public String getContent();
	
	/**
	 * @post getSelectedContent() != null.
	 * @return Le contenu à l'interieur des bornes de la sélection actualle.
	 */
	public String getSelectedContent();

	/**
	 * @pre s != null
	 * @post getContentAt(Selection s) != null
	 * @param s La sélection qui représente une partie du contenu qui nous interesse.
	 * @return Le contenu de cette selection.
	 */
	public String getContentAt(Selection s);
	
	@Override
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();

	/**
	 * Test si une selection est cohérente avec l'état actuel du moteur, c'est à dire que son début et sa fin sont dans des bornes valides.
	 * @param s La selection dont on veut verifié qu'elle est applicable.
	 * @return true si la selection est applicable pour executer une commande dessus.
	 */
	boolean isValidSelection(Selection s);
}