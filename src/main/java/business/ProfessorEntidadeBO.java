package main.java.business;

import java.util.List;

import main.java.model.beans.ProfessorEntidade;

public interface ProfessorEntidadeBO {
	public void createProfessorEntidade(List<ProfessorEntidade> relacionamentos)
			throws Exception;
}
