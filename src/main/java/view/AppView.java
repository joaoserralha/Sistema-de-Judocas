package main.java.view;

import main.java.facade.AppFacade;

public interface AppView {
	public void handleModelChange(Object obj);

	public void displayException(Exception e);

	public void registerFacade(AppFacade facade);
}
