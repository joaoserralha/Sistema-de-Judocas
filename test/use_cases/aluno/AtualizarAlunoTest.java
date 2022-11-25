package use_cases.aluno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mocks.AppViewMock;
import mocks.FacadeMock;
import main.java.model.beans.Aluno;
import main.java.model.beans.Endereco;
import main.java.model.beans.Entidade;
import main.java.model.beans.Filiado;
import main.java.model.beans.Professor;
import main.java.model.dao.DAO;
import main.java.model.dao.DAOImpl;
import main.java.util.DatabaseManager;

public class AtualizarAlunoTest {

    private static DAO<Aluno> alunoDao;
    private static Aluno aluno;
    private static Entidade entidade;
    private static Endereco endereco;
    private static Filiado f1;
    private static Filiado filiadoProf;
    private static Professor professor;
    private static AppViewMock view;
    private static FacadeMock facade;

    @BeforeClass
    public static void setUp() {
        DatabaseManager.setEnviroment(DatabaseManager.TEST);
        f1 = new Filiado();
        f1.setNome("John Doe");
        f1.setCpf("861.516.060-00");
        f1.setDataNascimento(new Date());
        f1.setDataCadastro(new Date());
        f1.setId(1332L);

        endereco = new Endereco();
        endereco.setBairro("Digory");
        endereco.setCep("64078-213");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");
        endereco.setRua("Rua Des. Berilo Mota");

        filiadoProf = new Filiado();
        filiadoProf.setNome("Professor");
        filiadoProf.setCpf("036.464.453-27");
        filiadoProf.setDataNascimento(new Date());
        filiadoProf.setDataCadastro(new Date());
        filiadoProf.setId(3332L);
        filiadoProf.setEndereco(endereco);

        professor = new Professor();
        professor.setFiliado(filiadoProf);

        entidade = new Entidade();
        entidade.setEndereco(endereco);
        entidade.setNome("Academia 1");
        entidade.setTelefone1("(086)1234-5432");

        aluno = new Aluno();
        aluno.setFiliado(f1);
        aluno.setProfessor(professor);
        aluno.setEntidade(entidade);

        alunoDao = new DAOImpl<Aluno>(Aluno.class);

        view = new AppViewMock();
        facade = view.facade;
        clearDatabase();
    }

    public static void clearDatabase() {
        List<Aluno> all = alunoDao.list();
        for (Aluno each : all) {
            alunoDao.delete(each);
        }
        assertEquals(0, alunoDao.list().size());
    }

    @Test
    public void updateStudent() throws Exception {
        facade.alunoBO.createAluno(aluno);

        // buscando usuario
        var alunoEncontrado = (facade.alunoBO.searchAluno(aluno)).get(0);

        // criando o objeto com os dados atualizados
        var filiado = aluno.getFiliado();
        filiado.setEmail("johnDnew@mail.com");

        alunoEncontrado.setFiliado(filiado);

        // atualizando
        facade.alunoBO.updateAluno(alunoEncontrado);

        var alunoAtualizado = facade.alunoBO.listAll().get(0);
        var updatedEmail = alunoAtualizado.getFiliado().getEmail();
        assertEquals(updatedEmail, "johnDnew@mail.com");
        clearDatabase();
    }

    @Test
    public void findByNameAndUpdateStudent() throws Exception {
        facade.alunoBO.createAluno(aluno);

        // Buscando aluno por nome
        Filiado filiadoBusca = new Filiado();

        filiadoBusca.setNome("John Doe");

        Aluno alunoBusca = new Aluno();
        alunoBusca.setFiliado(filiadoBusca);
        var alunoEncontrado = facade.alunoBO.searchAluno(alunoBusca).get(0);

        // Ccriando o objeto com os dados atualizados
        var filiado = aluno.getFiliado();
        filiado.setEmail("johnDnew@mail.com");

        alunoEncontrado.setFiliado(filiado);

        // Atualizando
        facade.alunoBO.updateAluno(alunoEncontrado);

        var alunoAtualizado = facade.alunoBO.listAll().get(0);
        var updatedEmail = alunoAtualizado.getFiliado().getEmail();
        assertEquals(updatedEmail, "johnDnew@mail.com");
        clearDatabase();
    }

    @Test
    public void updateStudentWithInvalidData() throws Exception {

        facade.alunoBO.createAluno(aluno);
        var invalidEmail = "mail.com";

        // buscando usuario
        var alunoEncontrado = (facade.alunoBO.searchAluno(aluno)).get(0);

        // Criando o objeto com os dados atualizados
        var filiado = aluno.getFiliado();
        filiado.setEmail(invalidEmail);

        alunoEncontrado.setFiliado(filiado);

        // Atualizando com os dados inválidos
        facade.alunoBO.updateAluno(alunoEncontrado);

        var alunoAtualizado = facade.alunoBO.listAll().get(0);
        var updatedEmail = alunoAtualizado.getFiliado().getEmail();

        assertNotEquals(invalidEmail, updatedEmail);
        assertEquals("Ocorreu um erro ao salvar os dados do aluno."
                + " Verifique se todos os dados foram preenchidos corretamente!", view.exceptionMessage);
        clearDatabase();
    }

    @Test
    public void UpdateStudentWithEmptyData() throws Exception {

        facade.alunoBO.createAluno(aluno);

        // buscando usuario
        var alunoEncontrado = (facade.alunoBO.searchAluno(aluno)).get(0);

        // Criando o objeto com os dados atualizados
        var filiado = aluno.getFiliado();
        filiado.setEmail("");
        filiado.setNome("");

        alunoEncontrado.setFiliado(filiado);

        // Atualizando com os dados inválidos
        facade.alunoBO.updateAluno(alunoEncontrado);

        var alunoAtualizado = facade.alunoBO.listAll().get(0);
        var updatedEmail = alunoAtualizado.getFiliado().getEmail();
        var updatedName = alunoAtualizado.getFiliado().getNome();

        assertNotEquals("", updatedEmail);
        assertNotEquals("", updatedName);
        assertEquals("Ocorreu um erro ao salvar os dados do aluno."
                + " Verifique se todos os dados foram preenchidos corretamente!", view.exceptionMessage);
        clearDatabase();
    }
}
