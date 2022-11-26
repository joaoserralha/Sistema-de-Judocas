package main.java.model.validator;

import main.java.model.beans.Professor;

public class ProfessorValidator implements Validator<Professor> {

    private boolean isPhoneValid(String str) {
        return str.matches("^\\(?\\d{2,3}\\)?(\\s)?\\d{4,5}-?\\d{4}$");
    }

    private boolean isCpfValid(String str) {
        return str.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
    }

    private boolean isEmailValid(String str) {
        return str.matches("^([a-zA-Z0-9]+)@.+.com$");
    }

    public boolean validate(Professor obj) {
        var professor = obj.getFiliado();

        if (professor.getNome() == "" || professor.getNome() == null) {
            return false;
        }
        if (professor.getCpf() == "" || professor.getCpf() == null || this.isCpfValid(professor.getCpf()) == false) {
            return false;
        }
        if (professor.getRg().getNumero() == null) {
            return false;
        }
        if (professor.getEmail() == "" || professor.getEmail() == null
                || this.isEmailValid(professor.getEmail()) == false) {
            return false;
        }
        if (professor.getTelefone1() == "" || professor.getTelefone1() == null
                || this.isPhoneValid(professor.getTelefone1()) == false) {
            return false;
        }
        return true;
    }
}