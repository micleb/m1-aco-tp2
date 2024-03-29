package fr.istic.m1.aco.miniediteur.v3.receiver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.istic.m1.aco.miniediteur.v3.receiver.Moteur;
import fr.istic.m1.aco.miniediteur.v3.receiver.MoteurImpl;
import fr.istic.m1.aco.miniediteur.v3.receiver.Selection;
import fr.istic.m1.aco.miniediteur.v3.receiver.SelectionImpl;

public class TestMoteur {
	Moteur m;
	Moteur simpleMoteur;
	
	@Before
	public void setUp() {
		StringBuffer b = new StringBuffer("Ce livre sur Samba est destiné aux informaticiens désirant mettre en œuvre un serveur de fichiers et d’impression sous GNU/Linux et accessible aux clients Windows et Mac. L’intégration au réseau de l’entreprise de cette implémentation libre du protocole SMB est transparente. Cet ouvrage couvre les distributions Fedora et Ubuntu.\n" + 
				"Le livre présente tout d’abord les bases indispensables sur les protocoles TCP/IP, les mécanismes de résolution de noms et les protocoles SMB/CIFS permettant d'appréhender au mieux le projet Samba. Il détaille ensuite l'installation et l'exploitation de celui-ci par le biais d'exemples tels que le partage simple de fichiers ou d'imprimantes ou l'accès à des ressources partagées par un poste Windows ou Mac. Les configurations sont réalisées aussi bien avec les outils graphiques des distributions qu'avec l'édition des fichiers de configuration.\n" + 
				"La seconde partie du livre donne au lecteur les clefs pour envisager une exploitation dans des environnements complexes, avec la gestion d'un domaine complet avec un annuaire LDAP, l’intégration et l'authentification dans un domaine Microsoft, la réplication du serveur de domaine, l'utilisation d'un serveur de DNS ainsi que la mise en place d'une solution anti-virus.\n" + 
				"Des éléments complémentaires sont en téléchargement sur www.editions-eni.fr.\n" + 
				"\n" + 
				"Les chapitres du livre :\n" + 
				"Avant-propos - Introduction – Installation – Partage de fichiers – Samba contrôleur de domaine – Accès aux partages Windows – Gestion des impressions – Gestion avancée");
		this.m = new MoteurImpl(b);	
		
		b = new StringBuffer("123456789ABCDEF");
		this.simpleMoteur = new MoteurImpl(b);
	}
	
	@Test
	public void testCopier() {
		Selection s = new SelectionImpl(331, 55);
		String expectedString = new StringBuffer("Le livre présente tout d’abord les bases indispensables").toString();
		m.selectionner(s);
		m.copier();
		String actualString = this.m.getPresspapierContent();
		assertEquals(expectedString, actualString);
	}
	
	@Test
	public void testCopierTout() {
		String expectedString = "123456789ABCDEF";
		Selection s = new SelectionImpl(0, 15);
		simpleMoteur.selectionner(s);
		assertEquals(simpleMoteur.getCurrentSelection(), s);
		simpleMoteur.copier();
		String actualString = this.simpleMoteur.getPresspapierContent();
		assertEquals(expectedString, actualString);
	}
	
	//TODO comportement si copier quand selection vide? (Sur atom : prend la ligne entière, sur eclipse : interdit
	/*@Test
	public void testCopierSelectionVide() {
		Selection s = new SelectionImpl(331, 33);
		String expectedString = new StringBuffer("").toString();
		m.selectionner(s);
		m.copier();
		String actualString = this.m.getPresspapierContent();
		assertEquals(expectedString, actualString);
	}*/
	
	@Test
	public void testCollerSelectionVide() {
		Selection s = new SelectionImpl(331, 56);
		m.selectionner(s);
		m.copier();
		
		//Le curseur est à la position 0 et il n'y a pas de selection donc ...
		s = new SelectionImpl(0, 0);
		m.selectionner(s);
		m.coller();
		
		
		//... Donc l'ajout de texte suite à la commande coller ajoute du texte sans ecraser du texte existant.
		String expectedString = new StringBuffer("Le livre présente tout d’abord les bases indispensables Ce livre sur Samba est destiné aux informaticiens désirant mettre en œuvre un serveur de fichiers et d’impression sous GNU/Linux et accessible aux clients Windows et Mac. L’intégration au réseau de l’entreprise de cette implémentation libre du protocole SMB est transparente. Cet ouvrage couvre les distributions Fedora et Ubuntu.\n" + 
				"Le livre présente tout d’abord les bases indispensables sur les protocoles TCP/IP, les mécanismes de résolution de noms et les protocoles SMB/CIFS permettant d'appréhender au mieux le projet Samba. Il détaille ensuite l'installation et l'exploitation de celui-ci par le biais d'exemples tels que le partage simple de fichiers ou d'imprimantes ou l'accès à des ressources partagées par un poste Windows ou Mac. Les configurations sont réalisées aussi bien avec les outils graphiques des distributions qu'avec l'édition des fichiers de configuration.\n" + 
				"La seconde partie du livre donne au lecteur les clefs pour envisager une exploitation dans des environnements complexes, avec la gestion d'un domaine complet avec un annuaire LDAP, l’intégration et l'authentification dans un domaine Microsoft, la réplication du serveur de domaine, l'utilisation d'un serveur de DNS ainsi que la mise en place d'une solution anti-virus.\n" + 
				"Des éléments complémentaires sont en téléchargement sur www.editions-eni.fr.\n" + 
				"\n" + 
				"Les chapitres du livre :\n" + 
				"Avant-propos - Introduction – Installation – Partage de fichiers – Samba contrôleur de domaine – Accès aux partages Windows – Gestion des impressions – Gestion avancée").toString();
		String actualString = this.m.getContent();
		assertEquals(expectedString, actualString);
	}
	
	@Test
	public void testCollerSelectionNonVide() {
		Selection s = new SelectionImpl(331, 55);
		m.selectionner(s);
		m.copier();
		
		//Le curseur est à la position 0 et il y a une selection de TAILLE 8 donc ...
		s = new SelectionImpl(0, 8);
		m.selectionner(s);
		m.coller();
		
		
		//... Donc l'ajout de texte suite à la commande coller ajoute du texte en écrasant les 8 premiers caractères.
		String expectedString = new StringBuffer("Le livre présente tout d’abord les bases indispensables sur Samba est destiné aux informaticiens désirant mettre en œuvre un serveur de fichiers et d’impression sous GNU/Linux et accessible aux clients Windows et Mac. L’intégration au réseau de l’entreprise de cette implémentation libre du protocole SMB est transparente. Cet ouvrage couvre les distributions Fedora et Ubuntu.\n" + 
				"Le livre présente tout d’abord les bases indispensables sur les protocoles TCP/IP, les mécanismes de résolution de noms et les protocoles SMB/CIFS permettant d'appréhender au mieux le projet Samba. Il détaille ensuite l'installation et l'exploitation de celui-ci par le biais d'exemples tels que le partage simple de fichiers ou d'imprimantes ou l'accès à des ressources partagées par un poste Windows ou Mac. Les configurations sont réalisées aussi bien avec les outils graphiques des distributions qu'avec l'édition des fichiers de configuration.\n" + 
				"La seconde partie du livre donne au lecteur les clefs pour envisager une exploitation dans des environnements complexes, avec la gestion d'un domaine complet avec un annuaire LDAP, l’intégration et l'authentification dans un domaine Microsoft, la réplication du serveur de domaine, l'utilisation d'un serveur de DNS ainsi que la mise en place d'une solution anti-virus.\n" + 
				"Des éléments complémentaires sont en téléchargement sur www.editions-eni.fr.\n" + 
				"\n" + 
				"Les chapitres du livre :\n" + 
				"Avant-propos - Introduction – Installation – Partage de fichiers – Samba contrôleur de domaine – Accès aux partages Windows – Gestion des impressions – Gestion avancée").toString();
		String actualString = this.m.getContent();
		assertEquals(expectedString, actualString);
	}
	
	@Test
	public void testCollerValiditeSelection() {
		Selection s = new SelectionImpl(0, 2);
		simpleMoteur.selectionner(s);
		simpleMoteur.copier();
		s = new SelectionImpl(10, 5);
		simpleMoteur.selectionner(s);
		simpleMoteur.coller();
		assertTrue(simpleMoteur.isValidSelection(simpleMoteur.getCurrentSelection()));
	}
	
	@Test
	public void testCouper() {
		//Du point de vu du presse papier, couper se comporte comme copier.
		Selection s = new SelectionImpl(331, 55);
		String expectedString = new StringBuffer("Le livre présente tout d’abord les bases indispensables").toString();
		m.selectionner(s);
		m.couper();
		String actualString = this.m.getPresspapierContent();
		assertEquals(expectedString, actualString);
	
		//On vérifie que la commande "couper" a bien supprimé le texte selectionné.
		String actualNewContent = m.getContent();
		String expectedNewContent = "Ce livre sur Samba est destiné aux informaticiens désirant mettre en œuvre un serveur de fichiers et d’impression sous GNU/Linux et accessible aux clients Windows et Mac. L’intégration au réseau de l’entreprise de cette implémentation libre du protocole SMB est transparente. Cet ouvrage couvre les distributions Fedora et Ubuntu.\n" + 
				" sur les protocoles TCP/IP, les mécanismes de résolution de noms et les protocoles SMB/CIFS permettant d'appréhender au mieux le projet Samba. Il détaille ensuite l'installation et l'exploitation de celui-ci par le biais d'exemples tels que le partage simple de fichiers ou d'imprimantes ou l'accès à des ressources partagées par un poste Windows ou Mac. Les configurations sont réalisées aussi bien avec les outils graphiques des distributions qu'avec l'édition des fichiers de configuration.\n" + 
				"La seconde partie du livre donne au lecteur les clefs pour envisager une exploitation dans des environnements complexes, avec la gestion d'un domaine complet avec un annuaire LDAP, l’intégration et l'authentification dans un domaine Microsoft, la réplication du serveur de domaine, l'utilisation d'un serveur de DNS ainsi que la mise en place d'une solution anti-virus.\n" + 
				"Des éléments complémentaires sont en téléchargement sur www.editions-eni.fr.\n" + 
				"\n" + 
				"Les chapitres du livre :\n" + 
				"Avant-propos - Introduction – Installation – Partage de fichiers – Samba contrôleur de domaine – Accès aux partages Windows – Gestion des impressions – Gestion avancée";
		assertEquals(expectedNewContent, actualNewContent);
	}
	
	@Test
	public void testInsererSelectionVide() {
		Selection s = new SelectionImpl(371, 0);
		
		//Pour tester, on change "les bases indispensables" par "les bases élémentaires vraiment indispensables".
		String toInsert =" élémentaires vraiment";
		m.selectionner(s);
		m.inserer(toInsert);
		
		String actualNewContent = m.getContent();
		String expectedNewContent = "Ce livre sur Samba est destiné aux informaticiens désirant mettre en œuvre un serveur de fichiers et d’impression sous GNU/Linux et accessible aux clients Windows et Mac. L’intégration au réseau de l’entreprise de cette implémentation libre du protocole SMB est transparente. Cet ouvrage couvre les distributions Fedora et Ubuntu.\n" + 
				"Le livre présente tout d’abord les bases élémentaires vraiment indispensables sur les protocoles TCP/IP, les mécanismes de résolution de noms et les protocoles SMB/CIFS permettant d'appréhender au mieux le projet Samba. Il détaille ensuite l'installation et l'exploitation de celui-ci par le biais d'exemples tels que le partage simple de fichiers ou d'imprimantes ou l'accès à des ressources partagées par un poste Windows ou Mac. Les configurations sont réalisées aussi bien avec les outils graphiques des distributions qu'avec l'édition des fichiers de configuration.\n" + 
				"La seconde partie du livre donne au lecteur les clefs pour envisager une exploitation dans des environnements complexes, avec la gestion d'un domaine complet avec un annuaire LDAP, l’intégration et l'authentification dans un domaine Microsoft, la réplication du serveur de domaine, l'utilisation d'un serveur de DNS ainsi que la mise en place d'une solution anti-virus.\n" + 
				"Des éléments complémentaires sont en téléchargement sur www.editions-eni.fr.\n" + 
				"\n" + 
				"Les chapitres du livre :\n" + 
				"Avant-propos - Introduction – Installation – Partage de fichiers – Samba contrôleur de domaine – Accès aux partages Windows – Gestion des impressions – Gestion avancée";
		assertEquals(expectedNewContent, actualNewContent);
	}
	
	@Test
	public void testInsererDebut() {
		Selection s = new SelectionImpl(0,0);
		simpleMoteur.selectionner(s);
		simpleMoteur.inserer("+-*/");
		
		assertEquals("+-*/123456789ABCDEF", simpleMoteur.getContent());
	}
	
	@Test
	public void testInsererFin() {
		Selection s = new SelectionImpl(15,0);
		simpleMoteur.selectionner(s);
		simpleMoteur.inserer("+-*/");
		
		assertEquals("123456789ABCDEF+-*/", simpleMoteur.getContent());
	}
	
	@Test
	public void testInsererEcraserTout() {
		Selection s = new SelectionImpl(0,15);
		simpleMoteur.selectionner(s);
		simpleMoteur.inserer("+-*/");
		assertTrue(simpleMoteur.isValidSelection(simpleMoteur.getCurrentSelection()));
		assertEquals("+-*/", simpleMoteur.getContent());
	}
	
	@Test
	public void testIsValidSelectionDebutVide() {
		Selection s = new SelectionImpl(0,0);
		assertTrue(simpleMoteur.isValidSelection(s));
	}
	
	@Test
	public void testIsValidSelection() {
		Selection s = new SelectionImpl(8, 2);
		assertTrue(simpleMoteur.isValidSelection(s));
	}
	
	@Test
	public void testIsValidSelectionFinVide() {
		Selection s = new SelectionImpl(15,0);
		assertTrue(simpleMoteur.isValidSelection(s));
	}
	
	@Test
	public void testIsValidSelectionHorsBorne() {
		Selection s = new SelectionImpl(10,9);
		assertFalse(simpleMoteur.isValidSelection(s));
		
		s = new SelectionImpl(10,6);
		assertFalse(simpleMoteur.isValidSelection(s));
	}
		
	@Test
	public void testIsValidSelectionTooBigSize() {
		Selection s = new SelectionImpl(0,16);
		assertFalse(simpleMoteur.isValidSelection(s));
	}
	
	@Test
	public void testSupprimer() {
		Selection s = new SelectionImpl(5,5);
		simpleMoteur.selectionner(s);
		simpleMoteur.supprimer();
		assertEquals("12345BCDEF",simpleMoteur.getContent());
	}
	
	@Test
	public void testSupprimerReplacementSelection() {
		Selection s = new SelectionImpl(10,5);
		simpleMoteur.selectionner(s);
		simpleMoteur.supprimer();
		assertEquals("123456789A",simpleMoteur.getContent());
		
		//La selection s n'est plus valide puisque les 5 dernièrs caractères ont été supprimés.
		//On vérifie que le moteur a bien corrigé sa selection.
		Selection sValide = new SelectionImpl(10, 0);
		assertEquals(sValide,simpleMoteur.getCurrentSelection());
	}
	
	@Test
	public void testSupprimerTout() {
		Selection s = new SelectionImpl(0,15);
		simpleMoteur.selectionner(s);
		simpleMoteur.supprimer();
		assertEquals("",simpleMoteur.getContent());
		
		
		//La selection s n'est plus valide puisque tout a été supprimé.
		//On vérifie que le moteur a bien corrigé sa selection.
		assertTrue(simpleMoteur.isValidSelection(simpleMoteur.getCurrentSelection()));
		Selection sValide = new SelectionImpl(0, 0);
		assertEquals(sValide,simpleMoteur.getCurrentSelection());
	}
	
	@Test 
	public void testGetContentAt() {
		Selection s = new SelectionImpl(7,6);
		String expectedContent = "89ABCD";
		String actualContent = simpleMoteur.getContentAt(s);
		assertEquals(expectedContent, actualContent);
	}
	
	@Test 
	public void testGetContentAtSelectionVide() {
		Selection s = new SelectionImpl(7,0);
		String expectedContent = "";
		String actualContent = simpleMoteur.getContentAt(s);
		assertEquals(expectedContent, actualContent);
	}
	
	@Test
	public void testGetSelectedContent() {
		Selection s = new SelectionImpl(7,6);
		simpleMoteur.selectionner(s);
		String expectedContent = "89ABCD";
		String actualContent = simpleMoteur.getSelectedContent();
		assertEquals(expectedContent, actualContent);
	}
	
	@Test
	public void testGetSelectedContentVide() {
		Selection s = new SelectionImpl(7,0);
		simpleMoteur.selectionner(s);
		String expectedContent = "";
		String actualContent = simpleMoteur.getSelectedContent();
		assertEquals(expectedContent, actualContent);
	}
}
