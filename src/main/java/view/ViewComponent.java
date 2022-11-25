package main.java.view;

import javax.swing.JPanel;

import main.java.facade.AppFacade;

public interface ViewComponent {
	public JPanel getGui();

	public void registerFacade(AppFacade fac);
}
