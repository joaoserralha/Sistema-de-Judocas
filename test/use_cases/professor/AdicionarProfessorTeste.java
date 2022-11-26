package use_cases.professor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
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

public class AdicionarProfessorTeste {
    private static DAO<Aluno> alunoDao;
    private static DAO<Professor> profDAO;
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
        f1.setEmail("johndoe@mail.com");
        f1.setDataNascimento(new Date());
        f1.setDataCadastro(new Date());
        f1.setTelefone1("(86)1233-4555");
        var rg1 = new Rg("531112224", "SSP");
        f1.setRg(rg1);
        f1.setId(1332L);

        endereco = new Endereco();
        endereco.setBairro("Rudge");
        endereco.setCep("64078-213");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");
        endereco.setRua("Rua Des. Berilo Mota");

        filiadoProf = new Filiado();
        filiadoProf.setNome("Digory");
        filiadoProf.setCpf("036.464.453-27");
        filiadoProf.setDataNascimento(new Date());
        filiadoProf.setDataCadastro(new Date());
        filiadoProf.setId(3332L);
        filiadoProf.setRegistroCbj("34561");
        filiadoProf.setEmail("digory@mail.com");
        filiadoProf.setTelefone1("(86)3333-4444");
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
        profDAO = new DAOImpl<Professor>(Professor.class);
        entidadeDao = new DAOImpl<Entidade>(Entidade.class);

        view = new AppViewMock();
        facade = view.facade;
        clearDatabase();
    }

    public static void clearDatabase() {
        List<Aluno> allStudents = alunoDao.list();
        for (Aluno each : allStudents) {
            alunoDao.delete(each);
        }
        assertEquals(0, alunoDao.list().size());

        List<Professor> allTeachers = profDAO.list();
        for (Professor each : allTeachers) {
            profDAO.delete(each);
        }
        assertEquals(0, alunoDao.list().size());

        List<Entidade> allEntities = entidadeDao.list();
        for (Entidade each : allEntities) {
            entidadeDao.delete(each);
        }
        assertEquals(0, alunoDao.list().size());
    }

    @Test
    public void createValidProfessor() throws Exception {

        facade.professorBO.createProfessor(professor);
        var result = facade.professorBO.listAll();
        var message = view.exceptionMessage;

        assertNotNull(result);
        assertEquals(result.get(0).getFiliado().getNome(), "Digory");
        assertEquals(result.get(0).getFiliado().getRegistroCbj(), "34561");
        assertNull(message);
        clearDatabase();
    }

    @Test
    public void searchInvalidProfessor() throws Exception {

        var f = new Filiado();
        f.setNome("");
        f.setCpf("");
        f.setEmail("@");
        f.setTelefone1("");
        f.setRg(null);

        // Buscando entidade por nome
        Professor professorBusca = new Professor();
        Filiado filiadoBusca = new Filiado();
        filiadoBusca.setNome("Edmundo");
        professorBusca.setFiliado(filiadoBusca);

        List<Professor> profs = facade.professorBO.searchProfessor(professorBusca);

        assertTrue(profs.isEmpty());
        clearDatabase();
    }

    @Test
    public void createProfessorNotRegistered() throws Exception {

        filiadoProf = new Filiado();
        filiadoProf.setNome("Digory");
        filiadoProf.setCpf("036.464.453-27");
        filiadoProf.setDataNascimento(new Date());
        filiadoProf.setDataCadastro(new Date());
        filiadoProf.setId((long) 10);
        filiadoProf.setRegistroCbj("34561");
        filiadoProf.setEmail("digory@mail.com");
        filiadoProf.setTelefone1("(86)3333-4444");
        var rg2 = new Rg("531112224", "SSP");
        filiadoProf.setRg(rg2);
        filiadoProf.setEndereco(endereco);

        professor = new Professor();
        professor.setFiliado(filiadoProf);

        facade.professorBO.createProfessor(professor);

        var result = facade.professorBO.listAll();

        assertTrue(result.get(0).getFiliado().getCpf().equals("036.464.453-27"));
        clearDatabase();
    }

    @Test
    public void createProfessorWithInvalidFields() throws Exception {

        var f = new Filiado();
        f.setNome("Digory");
        f.setCpf("158115");
        f.setEmail("@mail");
        f.setDataNascimento(new Date());
        f.setDataCadastro(new Date());
        f.setId(1332L);

        var professorInvalido = professor;
        professorInvalido.setFiliado(f);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            facade.professorBO.createProfessor(professorInvalido);
        });

        assertEquals("Ocorreu um erro ao cadastrar o professor!"
                + " Verifique se todos os dados foram preenchidos corretamente.", thrown.getMessage());
        clearDatabase();
    }

    @Test
    public void createProfessorWithBlankFields() throws Exception {

        var f = new Filiado();
        f.setNome("");
        f.setCpf("");
        f.setEmail("@");
        f.setTelefone1("");
        f.setRg(null);

        var professorInvalido = professor;
        professorInvalido.setFiliado(f);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            facade.professorBO.createProfessor(professorInvalido);
        });

        assertEquals("Ocorreu um erro ao cadastrar o professor!"
                + " Verifique se todos os dados foram preenchidos corretamente.", thrown.getMessage());
        clearDatabase();
    }

}
