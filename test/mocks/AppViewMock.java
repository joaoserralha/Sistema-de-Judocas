package mocks;

import main.java.facade.AppFacade;
import main.java.view.AppView;

public class AppViewMock implements AppView {
    public FacadeMock facade;
    public String exceptionMessage;

    public AppViewMock() {
        facade = new FacadeMock(this);
    }

    @Override
    public void handleModelChange(Object obj) {
    }

    @Override
    public void displayException(Exception e) {
        this.exceptionMessage = e.getMessage();
    }

    @Override
    public void registerFacade(AppFacade facade) {

    }
}