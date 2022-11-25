package main.java.model.validator;

import main.java.model.beans.Aluno;

public class AlunoValidator implements Validator<Aluno> {
	public boolean validate(Aluno obj) {
		return true;
	}
}