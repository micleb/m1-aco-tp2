package fr.istic.m1.aco.miniediteur.v3.invoker;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Doublure de test pour les tests unitaires
 * Utile lors des tests unitaires où l'on utilise des fonctionnalités qui demande des informations à l'utilisateur.
 * Cette implémentation spéciale imite une réponse de l'utilisateur.
 * Il est possible d'ajouter et d'utiliser plusieurs réponses à la suite.
 * 
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
		
	}
}