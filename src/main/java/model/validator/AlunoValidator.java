package main.java.model.validator;

import main.java.model.beans.Aluno;

public class AlunoValidator implements Validator<Aluno> {

	private boolean isPhoneValid(String str) {
		return str.matches("^\\(?\\d{0,3}\\)?\\s?(\\d{6,9}|\\d{3,5}\\-\\d{3,5})$");
	}

	private boolean isCpfValid(String str) {
		return str.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
	}

	private boolean isEmailValid(String str) {
		return str.matches("^([a-z0-9]+)@.+.com$");
	}

	public boolean validate(Aluno obj) {
		var aluno = obj.getFiliado();

		if (aluno.getNome() == "" || aluno.getNome() == null) {
			return false;
		}
		if (aluno.getCpf() == "" || aluno.getCpf() == null || this.isCpfValid(aluno.getCpf()) == false) {
			return false;
		}
		if (aluno.getRg() == null) {
			return false;
		}
		if (aluno.getEmail() == "" || aluno.getEmail() == null || this.isEmailValid(aluno.getEmail()) == false) {
			return false;
		}
		if (aluno.getTelefone1() == "" || aluno.getTelefone1() == null
				|| this.isPhoneValid(aluno.getTelefone1()) == false) {
			return false;
		}
		return true;
	}
}