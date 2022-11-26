package main.java.model.validator;

import main.java.model.beans.Entidade;

public class EntidadeValidator implements Validator<Entidade> {

    private boolean isPhoneValid(String str) {
        return str.matches("^\\(?\\d{2,3}\\)?(\\s)?\\d{4,5}-?\\d{4}$");
    }

    private boolean isCnpjValid(String str) {
        return str.matches("^.{14}|.{18}$");
    }

    public boolean validate(Entidade entidade) {

        if (entidade.getNome() == "" || entidade.getNome() == null) {
            return false;
        }
        if (entidade.getCnpj() == "" || entidade.getCnpj() == null || this.isCnpjValid(entidade.getCnpj()) == false) {
            return false;
        }
        if (entidade.getTelefone1() == "" || entidade.getTelefone1() == null
                || this.isPhoneValid(entidade.getTelefone1()) == false) {
            return false;
        }
        return true;
    }
}