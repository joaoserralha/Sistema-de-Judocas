package main.java.business;

import java.util.List;

import main.java.model.beans.Entidade;

public interface EntidadeBO {
	public void createEntidade(Entidade entidade) throws Exception;

	public void updateEntidade(Entidade entidade) throws Exception;

	public List<Entidade> searchEntidade(Entidade entidade) throws Exception;

	public List<Entidade> listAll() throws Exception;
}
