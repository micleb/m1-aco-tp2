package fr.istic.m1.aco.miniediteur.v3.invoker;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author bzherlb
 * Doublure de test pour les tests unitaires
 * Utile lors des tests unitaires où l'on utilise des fonctionnalités qui demande des informations à l'utilisateur.
 * Cette implémentation spéciale imite une réponse de l'utilisateur.
 * Il est possible d'ajouter et d'utiliser plusieurs réponses à la suite.
 * 
 * --- (cpt)
 * Lors des tests unitaires, on peut vouloir utiliser et tester des commandes.
 * Problèmes : certaines commandes, tel que sélectionner ou inserer, demande à l'utilisateur 
 * une intervention pour choisir des paramètres. 
 * Pour rappel on avait fait ça dans le but d'éviter à l'ihm de devoir connaitre en avance les arguments d'une commande, pas compatible avec du polymorphisme sur une méthode très générique.
 * 
 * On pourrait lors des tests unitaires utiliser une vraie implémentation d'ihm mais cela perdrait de son interêt puisque ces tests ne serait plus automatique.
 * 
 * La solution classique à ce problème est utiliser une doublure de test.
 * Cette doublure permet d'imiter le comportement d'une vraie IHM, comme si l'utilisateur avait lui même tapez la réponse.
 */
public class StubIHM implements IHM {

	private Deque<String> fakeUserString;
	private Deque<Integer> fakeUserInt;
	
	public StubIHM() {
		fakeUserString = new ArrayDeque<>();
		fakeUserInt=new ArrayDeque<>();
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public int requestInt(String prompt) {
		if (fakeUserInt.isEmpty()) {
			throw new IllegalStateException("Il n'y a pas de réponse pré-construite pour requestInt. "
			+ "Vérifiez dans votre test unitaire que vous avez ajouter une réponse simulée via addFakeUserResponse(int userResponse)");
		}
		return fakeUserInt.pop();
	}

	@Override
	public String requestString(String prompt) {
		if (fakeUserString.isEmpty()) {
			throw new IllegalStateException("Il n'y a pas de réponse pré-construite pour requestString."
			+ "Vérifiez dans votre test unitaire que vous avez ajouter une réponse simulée via addFakeUserResponse(String userResponse)");
		} 
		return fakeUserString.pop();
	}

	public void addFakeUserResponse(String userResponse) {
		fakeUserString.add(userResponse);
	}
	
	public void addFakeUserResponse(int userResponse) {
		fakeUserInt.add(Integer.valueOf(userResponse));
	}

	@Override
	public void notifyUser(String string) {
		// TODO Auto-generated method stub
		
	}
}