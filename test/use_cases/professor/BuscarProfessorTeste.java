package use_cases.professor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

public class BuscarProfessorTeste {
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
    public void clearDatabaseTest() {
        clearDatabase();
        List<Aluno> all = alunoDao.list();
        List<Professor> professores = profDAO.list();

        assertEquals(0, all.size());
        assertEquals(0, professores.size());
    }

    @Test
    public void findByName() throws Exception {
        facade.professorBO.createProfessor(professor);

        Filiado filiadoBusca = new Filiado();
        filiadoBusca.setNome("Digory");

        Professor searchProf = new Professor();
        searchProf.setFiliado(filiadoBusca);

        var prof = facade.professorBO.searchProfessor(searchProf).get(0);

        assertEquals(prof.getFiliado().getNome(), searchProf.getFiliado().getNome());
        clearDatabase();
    }

    @Test
    public void searchNonExistentProfessor() throws Exception {
        facade.professorBO.createProfessor(professor);

        Filiado filiadoBusca = new Filiado();
        filiadoBusca.setNome("Bob");

        Professor searchProf = new Professor();
        searchProf.setFiliado(filiadoBusca);

        var listaAlunos = facade.professorBO.searchProfessor(searchProf);

        assertTrue(listaAlunos.isEmpty());
        clearDatabase();
    }

    @AfterClass
    public static void closeDatabase() {
        clearDatabase();
    }

}
