package main.view;

import javax.swing.JPanel;

import main.facade.AppFacade;

public interface ViewComponent {
	public JPanel getGui();

	public void registerFacade(AppFacade fac);
}
