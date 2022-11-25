package use_cases.aluno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import main.java.model.beans.Rg;
import main.java.model.dao.DAO;
import main.java.model.dao.DAOImpl;
import main.java.util.DatabaseManager;

public class AdicionarAlunoTest {

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
        f1.setTelefone1("(86)1233-4555");
        var rg1 = new Rg("531112224", "SSP");
        f1.setRg(rg1);
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
        var rg2 = new Rg("531112224", "SSP");
        filiadoProf.setRg(rg2);
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
    }

    public static void clearDatabase() {
        List<Aluno> all = alunoDao.list();
        for (Aluno each : all) {
            alunoDao.delete(each);
        }
        assertEquals(0, alunoDao.list().size());
    }

    @Test
    public void createValidStudent() throws Exception {

        facade.alunoBO.createAluno(aluno);
        var result = facade.alunoBO.listAll();

        var message = view.exceptionMessage;

        assertNotNull(result);
        assertNull(message);
        clearDatabase();
    }

    @Test
    public void createStudentWithInvalidFields() throws Exception {

        var f = new Filiado();
        f.setNome("John Doe");
        f.setCpf("861.516.060");
        f.setEmail("@mail");
        f.setDataNascimento(new Date());
        f.setDataCadastro(new Date());
        f.setId(1332L);

        var alunoInvalido = aluno;
        alunoInvalido.setFiliado(f);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            facade.alunoBO.createAluno(alunoInvalido);
        });
        assertEquals("Ocorreu um erro ao cadastrar o aluno!"
                + " Verifique se todos os dados foram preenchidos corretamente.", thrown.getMessage());
        clearDatabase();
    }

    @Test
    public void createStudentWithMissingFields() throws Exception {

        var f = new Filiado();
        f.setNome(null);
        f.setCpf(null);
        f.setEmail(null);
        f.setDataNascimento(null);
        f.setDataCadastro(null);
        f.setId(1332L);

        var alunoInvalido = aluno;
        alunoInvalido.setFiliado(f);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            facade.alunoBO.createAluno(alunoInvalido);
        });
        assertEquals("Ocorreu um erro ao cadastrar o aluno!"
                + " Verifique se todos os dados foram preenchidos corretamente.", thrown.getMessage());
        clearDatabase();
    }

    @AfterClass
    public static void closeDatabase() {
        clearDatabase();
    }
}
