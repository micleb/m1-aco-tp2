package fr.istic.m1.aco.miniediteur.v1.receiver;

public interface Selection {
	public int getLength();
	public int getStartIndex();
	public int getEndIndex();
	boolean isEmpty();
}