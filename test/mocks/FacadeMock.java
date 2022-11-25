package mocks;

import javax.swing.JOptionPane;
import net.java.dev.genesis.annotation.Form;
import main.java.business.AlunoBO;
import main.java.business.AlunoBOImpl;
import main.java.business.EntidadeBO;
import main.java.business.EntidadeBOImpl;
import main.java.business.ProfessorBO;
import main.java.business.ProfessorBOImpl;
import main.java.business.ProfessorEntidadeBO;
import main.java.business.ProfessorEntidadeBOImpl;
import main.java.facade.AppFacade;
import main.java.view.AppView;
import main.java.view.MainAppView;
import main.java.view.gui.MainAppFrame;

@Form
public class FacadeMock implements AppView {

    private AppView view = new MainAppView();
    public AlunoBO alunoBO;
    public ProfessorBO professorBO;
    public EntidadeBO entidadeBO;
    public ProfessorEntidadeBO professorEntidadeBO;

    public FacadeMock() {
        this.alunoBO = new AlunoBOImpl(this.view);
        this.professorBO = new ProfessorBOImpl(this.view);
        this.entidadeBO = new EntidadeBOImpl(this.view);
        this.professorEntidadeBO = new ProfessorEntidadeBOImpl(this.view);
    }

    @Override
    public void handleModelChange(Object obj) {

    }

    @Override
    public void displayException(Exception e) {

    }

    @Override
    public void registerFacade(AppFacade facade) {

    }
}