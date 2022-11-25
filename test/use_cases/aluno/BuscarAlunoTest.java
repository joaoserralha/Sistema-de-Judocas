package use_cases.aluno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

public class BuscarAlunoTest {

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
    public void findByName() throws Exception {
        facade.alunoBO.createAluno(aluno);

        // Buscando aluno por nome
        Filiado filiadoBusca = new Filiado();
        filiadoBusca.setNome("John Doe");

        Aluno alunoBusca = new Aluno();
        alunoBusca.setFiliado(filiadoBusca);

        var alunoEncontrado = facade.alunoBO.searchAluno(alunoBusca).get(0);

        assertEquals(aluno, alunoEncontrado);
        clearDatabase();
    }

    @Test
    public void searchNonexistent() throws Exception {
        facade.alunoBO.createAluno(aluno);

        // Buscando aluno por nome
        Filiado filiadoBusca = new Filiado();
        filiadoBusca.setNome("Bob");

        Aluno alunoBusca = new Aluno();
        alunoBusca.setFiliado(filiadoBusca);

        var listaAlunos = facade.alunoBO.searchAluno(alunoBusca);

        // lista deve ser vazia
        assertTrue(listaAlunos.isEmpty());
        clearDatabase();
    }

    @AfterClass
    public static void closeDatabase() {
        clearDatabase();
    }
}
