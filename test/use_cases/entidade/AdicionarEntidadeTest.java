package use_cases.entidade;

import static org.junit.Assert.assertEquals;
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

public class AdicionarEntidadeTest {

    private static DAO<Aluno> alunoDao;
    private static DAO<Professor> professorDao;
    private static DAO<Entidade> entidadeDao;
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
        endereco.setRua("Deputado Benoni Portela");
        endereco.setNumero("880");
        endereco.setBairro("Bairro Gurupi");

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
        entidade.setNome("JudoPlus");
        entidade.setTelefone1("(086)1234-5432");
        entidade.setCnpj("62.055.474/0001-98");

        aluno = new Aluno();
        aluno.setFiliado(f1);
        aluno.setProfessor(professor);
        aluno.setEntidade(entidade);

        alunoDao = new DAOImpl<Aluno>(Aluno.class);
        professorDao = new DAOImpl<Professor>(Professor.class);
        entidadeDao = new DAOImpl<Entidade>(Entidade.class);

        view = new AppViewMock();
        facade = view.facade;
    }

    public static void clearDatabase() {
        List<Aluno> allStudents = alunoDao.list();
        for (Aluno each : allStudents) {
            alunoDao.delete(each);
        }
        assertEquals(0, alunoDao.list().size());

        List<Professor> allTeachers = professorDao.list();
        for (Professor each : allTeachers) {
            professorDao.delete(each);
        }
        assertEquals(0, alunoDao.list().size());

        List<Entidade> allEntities = entidadeDao.list();
        for (Entidade each : allEntities) {
            entidadeDao.delete(each);
        }
        assertEquals(0, alunoDao.list().size());
    }

    @Test
    public void createValidEntity() throws Exception {

        facade.entidadeBO.createEntidade(entidade);
        var result = facade.entidadeBO.listAll();

        var message = view.exceptionMessage;

        assertNotNull(result);
        assertNull(message);
        clearDatabase();
    }

    @Test
    public void createEntityWithInvalidFields() throws Exception {

        var entidadeInvalida = entidade;

        entidadeInvalida.setCnpj("62055474");
        entidadeInvalida.setTelefone1("as25145");

        facade.entidadeBO.createEntidade(entidadeInvalida);
        var result = facade.entidadeBO.listAll();

        var message = view.exceptionMessage;

        assertNotNull(result);
        assertEquals("Ocorreu um erro ao cadastrar a entidade!"
                + " Verifique se todos os dados foram preenchidos corretamente.", message);
        clearDatabase();
    }

    @Test
    public void createEntityWithMissingFields() throws Exception {

        var entidadeInvalida = entidade;

        entidadeInvalida.setCnpj("");
        entidadeInvalida.setTelefone1("");

        facade.entidadeBO.createEntidade(entidadeInvalida);
        var result = facade.entidadeBO.listAll();

        var message = view.exceptionMessage;

        assertNotNull(result);
        assertEquals("Ocorreu um erro ao cadastrar a entidade!"
                + " Verifique se todos os dados foram preenchidos corretamente.", message);
        clearDatabase();
    }

    @AfterClass
    public static void closeDatabase() {
        clearDatabase();
    }
}
