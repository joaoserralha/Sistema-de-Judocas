package main.java.facade;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import main.java.business.AlunoBO;
import main.java.business.AlunoBOImpl;
import main.java.business.EntidadeBO;
import main.java.business.EntidadeBOImpl;
import main.java.business.ProfessorBO;
import main.java.business.ProfessorBOImpl;
import main.java.business.ProfessorEntidadeBO;
import main.java.business.ProfessorEntidadeBOImpl;
import main.java.model.beans.Aluno;
import main.java.model.beans.Entidade;
import main.java.model.beans.Professor;
import main.java.model.beans.ProfessorEntidade;
import main.java.view.AppView;

public class AppFacadeImpl implements AppFacade {

	private AppView view;
	private AlunoBO alunoBO;
	private ProfessorBO professorBO;
	private EntidadeBO entidadeBO;
	private ProfessorEntidadeBO professorEntidadeBO;

	public AppFacadeImpl(AppView view) {
		this.view = view;
		this.alunoBO = new AlunoBOImpl(view);
		this.professorBO = new ProfessorBOImpl(view);
		this.entidadeBO = new EntidadeBOImpl(view);
		this.professorEntidadeBO = new ProfessorEntidadeBOImpl(view);
	}

	private void reportException(Exception e) {
		view.displayException(e);
	}

	@Override
	public void createAluno(Aluno aluno) {
		System.out.println("AppFacadeImpl.createAluno()");
		try {
			alunoBO.createAluno(aluno);
		} catch (Exception e) {
			reportException(e);
		}
	}

	@Override
	public void updateAluno(Aluno aluno) {
		try {
			alunoBO.updateAluno(aluno);
		} catch (Exception e) {
			reportException(e);
		}
	}

	@Override
	public List<Aluno> searchAluno(Aluno aluno) {
		List<Aluno> result = new ArrayList<Aluno>();
		try {
			result = alunoBO.searchAluno(aluno);
		} catch (Exception e) {
			reportException(e);
		}
		return result;
	}

	@Override
	public void listAlunos() {
		try {
			alunoBO.listAll();
		} catch (Exception e) {
			reportException(e);
		}
	}

	@Override
	public void createProfessor(Professor professor) {
		try {
			professorBO.createProfessor(professor);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			reportException(e);
		}
	}

	@Override
	public List<Professor> listProfessores() {
		List<Professor> result = null;
		try {
			result = professorBO.listAll();
		} catch (Exception e) {
			reportException(e);
		}
		return result;
	}

	@Override
	public List<Professor> searchProfessor(Professor professor) {
		List<Professor> result = null;
		try {
			result = professorBO.searchProfessor(professor);
		} catch (Exception e) {
			reportException(e);
		}
		return result;
	}

	@Override
	public void updateProfessor(Professor professor) {
		try {
			professorBO.updateProfessor(professor);
		} catch (Exception e) {
			reportException(e);
		}
	}

	@Override
	public void createEntidade(Entidade entidade) {
		try {
			entidadeBO.createEntidade(entidade);
		} catch (Exception e) {
			reportException(e);
		}
	}

	@Override
	public List<Entidade> listEntidade() {
		List<Entidade> result = null;
		try {
			result = entidadeBO.listAll();
		} catch (Exception e) {
			reportException(e);
		}
		return result;
	}

	@Override
	public List<Entidade> searchEntidade(Entidade entidade) {
		List<Entidade> result = null;
		try {
			result = entidadeBO.searchEntidade(entidade);
		} catch (Exception e) {
			reportException(e);
		}
		return result;
	}

	@Override
	public void updateEntidade(Entidade entidade) {
		try {
			entidadeBO.updateEntidade(entidade);
		} catch (Exception e) {
			reportException(e);
		}
	}

	@Override
	public void createProfessorEntidade(List<ProfessorEntidade> relacionamentos) {
		try {
			professorEntidadeBO.createProfessorEntidade(relacionamentos);
		} catch (Exception e) {
			reportException(e);
		}
	}
}
