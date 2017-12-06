package fr.istic.m1.aco.miniediteur.v3.command;
/**
 * Une liste abstraite de commande, indépendament d'une implémentation particulière.
 *
 */
public enum CommandEnum {
	SELECT,
	INSERT,
	COPY,
	CUT,
	PASTE,
	START_RECORDING,
	STOP_RECORDING,
	REPLAY,
	UNDO,
	REDO, 
	DELETE;
}
