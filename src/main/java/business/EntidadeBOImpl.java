package main.java.business;

import java.util.List;

import main.java.model.beans.Entidade;
import main.java.model.dao.DAO;
import main.java.model.dao.DAOImpl;
import main.java.model.validator.EntidadeValidator;
import main.java.view.AppView;

public class EntidadeBOImpl implements EntidadeBO {
	private static EntidadeValidator validator = new EntidadeValidator();
	private AppView view;
	private static DAO<Entidade> dao = new DAOImpl<Entidade>(Entidade.class, validator, false);

	public EntidadeBOImpl(AppView view) {
		this.view = view;
	}

	private void fireModelChangeEvent(Entidade entidade) {
		view.handleModelChange(entidade);
	}

	@Override
	public void createEntidade(Entidade entidade) throws Exception {
		System.out.println("EntidadeBOImpl.createEntidade()");
		try {
			dao.save(entidade);
			fireModelChangeEvent(entidade);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao cadastrar a entidade!"
					+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao salvar a entidade.");
		}
	}

	@Override
	public List<Entidade> listAll() throws Exception {
		List<Entidade> result;
		try {
			result = dao.list();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao listar entidades!"
					+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao listar entidades.");
		}
		return result;
	}

	@Override
	public List<Entidade> searchEntidade(Entidade entidade) throws Exception {
		List<Entidade> result;
		try {
			result = dao.search(entidade);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao buscar entidades!"
					+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao buscar entidades.");
		}
		return result;
	}

	@Override
	public void updateEntidade(Entidade entidade) throws Exception {
		try {
			dao.save(entidade);
			fireModelChangeEvent(entidade);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao atualizar a entidade!"
					+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao atualizar a entidade.");
		}
	}
}
