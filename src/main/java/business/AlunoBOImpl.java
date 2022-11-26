package main.java.business;

import java.util.List;

import main.java.model.beans.Aluno;
import main.java.model.dao.DAO;
import main.java.model.dao.DAOImpl;
import main.java.util.FiliadoID;
import main.java.view.AppView;

import main.java.model.validator.AlunoValidator;

public class AlunoBOImpl implements AlunoBO {
	private static AlunoValidator validator = new AlunoValidator();
	private static DAO<Aluno> dao = new DAOImpl<Aluno>(Aluno.class, validator, false);
	private AppView view;

	public AlunoBOImpl(AppView view) {
		this.view = view;
	}

	private void fireModelChangeEvent(Aluno aluno) {
		view.handleModelChange(aluno);
	}

	@Override
	public void createAluno(Aluno aluno) throws Exception {
		System.out.println("AlunoBOImpl.createAluno()");
		try {
			aluno.getFiliado().setId(FiliadoID.getNextID());
			dao.save(aluno);
			fireModelChangeEvent(aluno);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao cadastrar o aluno!"
					+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void updateAluno(Aluno aluno) throws Exception {
		try {
			Aluno old = null;
			old = dao.get(aluno);
			if (old != null) {
				if (validator.validate(aluno)) {
					old.copyProperties(aluno);
				} else {
					throw new IllegalArgumentException();
				}
			}
			fireModelChangeEvent(old);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Ocorreu um erro ao salvar os dados do aluno."
					+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao salvar o aluno.");
		}
	}

	@Override
	public List<Aluno> searchAluno(Aluno aluno) throws Exception {
		List<Aluno> result;
		try {
			result = dao.search(aluno);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao salvar os dados do aluno."
					+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao buscar os aluno.");
		}
		return result;
	}

	@Override
	public List<Aluno> listAll() throws Exception {
		List<Aluno> result;
		try {
			result = dao.list();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao obter a lista de alunos."
					+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido o obter a lista de alunos.");
		}
		return result;
	}
}