package main.java.business;

import java.util.List;

import main.java.model.beans.Professor;
import main.java.model.dao.DAO;
import main.java.model.dao.DAOImpl;
import main.java.model.validator.ProfessorValidator;
import main.java.util.FiliadoID;
import main.java.view.AppView;

public class ProfessorBOImpl implements ProfessorBO {
	private static ProfessorValidator validator = new ProfessorValidator();
	private AppView view;
	private static DAO<Professor> dao = new DAOImpl<Professor>(Professor.class, validator, false);

	public ProfessorBOImpl(AppView view) {
		this.view = view;
	}

	private void fireModelChangeEvent(Professor professor) {
		view.handleModelChange(professor);
	}

	@Override
	public void createProfessor(Professor professor) throws Exception {
		try {
			professor.getFiliado().setId(FiliadoID.getNextID());
			dao.save(professor);
			fireModelChangeEvent(professor);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao cadastrar o professor!"
					+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao salvar o professor.");
		}
	}

	@Override
	public void updateProfessor(Professor professor) throws Exception {
		try {
			dao.save(professor);
			fireModelChangeEvent(professor);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao atualizar os dados do professor!"
					+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao atualizar o professor.");
		}
	}

	@Override
	public List<Professor> listAll() throws Exception {
		List<Professor> result;
		try {
			result = dao.list();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao listar de professores."
					+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao listar professores.");
		}
		return result;
	}

	@Override
	public List<Professor> searchProfessor(Professor professor)
			throws Exception {
		List<Professor> result;
		try {
			result = dao.search(professor);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao buscar os professores."
					+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao buscar os professores.");
		}
		return result;
	}
}
