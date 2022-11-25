package main.model.validator;

import main.model.beans.Aluno;

public class AlunoValidator implements Validator<Aluno> {
	public boolean validate(Aluno obj) {
		return true;
	}
}