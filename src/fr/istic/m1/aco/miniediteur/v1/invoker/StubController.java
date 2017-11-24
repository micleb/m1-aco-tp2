package fr.istic.m1.aco.miniediteur.v1.invoker;

import fr.istic.m1.aco.miniediteur.v1.receiver.Selection;

public class StubController implements Controller {

	private String fakeText;
	private Selection fakeSelection;
	private String fakeLastInseredContent;
	
	
	public StubController(String fakeText, Selection fakeSelection, String fakeLastInseredContent) {
		super();
		this.fakeText = fakeText;
		this.fakeSelection = fakeSelection;
		this.fakeLastInseredContent = fakeLastInseredContent;
	}

	@Override
	public String getText() {
		return fakeText;
	}

	@Override
	public Selection getSelection() {
		return fakeSelection;
	}

	@Override
	public String getLastInseredContent() {
		return fakeLastInseredContent ;
	}

}
