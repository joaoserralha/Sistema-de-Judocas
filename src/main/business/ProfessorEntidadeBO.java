package main.business;

import java.util.List;

import main.model.beans.ProfessorEntidade;

public interface ProfessorEntidadeBO {
	public void createProfessorEntidade(List<ProfessorEntidade> relacionamentos)
			throws Exception;
}
