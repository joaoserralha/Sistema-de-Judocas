package main.facade;

import java.util.List;

import main.model.beans.Aluno;
import main.model.beans.Entidade;
import main.model.beans.Professor;
import main.model.beans.ProfessorEntidade;

public interface AppFacade {

	public abstract void createAluno(Aluno aluno);

	public abstract void updateAluno(Aluno aluno);

	public abstract List<Aluno> searchAluno(Aluno aluno);

	public abstract void listAlunos();

	public abstract void createProfessor(Professor professor);

	public abstract void updateProfessor(Professor professor);

	public abstract List<Professor> searchProfessor(Professor professor);

	public abstract List<Professor> listProfessores();

	public abstract void createEntidade(Entidade entidade);

	public abstract void updateEntidade(Entidade entidade);

	public abstract List<Entidade> searchEntidade(Entidade entidade);

	public abstract List<Entidade> listEntidade();

	public abstract void createProfessorEntidade(List<ProfessorEntidade> relacionamentos);

}