package AgendaContactos;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FicheiroContatosTest {

    private File tempFile;
    private FicheiroContatos repo;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("contatos_test", ".txt");
        repo = new FicheiroContatos(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void testSalvarECarregarContatos() throws IOException {
        List<Contato> contatosOriginais = List.of(
                new Contato("João", "+351912345678", "joao@email.com"),
                new Contato("Maria", "+351923456789", "maria@email.com")
        );

        repo.salvar(contatosOriginais);
        List<Contato> carregados = repo.carregar();
        assertEquals(2, carregados.size());
        assertEquals("João", carregados.get(0).getNome());
        assertEquals("+351912345678", carregados.get(0).getTelefone());
        assertEquals("joao@email.com", carregados.get(0).getEmail());

        assertEquals("Maria", carregados.get(1).getNome());
        assertEquals("+351923456789", carregados.get(1).getTelefone());
        assertEquals("maria@email.com", carregados.get(1).getEmail());
    }

    @Test
    void testCarregarContatosComFicheiroVazio() throws IOException {
        List<Contato> carregados = repo.carregar();
        assertTrue(carregados.isEmpty(), "A lista de contatos deve estar vazia.");
    }

    @Test
    void testSalvarContatosComExcecao() {
        FicheiroContatos repoComFicheiroInexistente = new FicheiroContatos("/caminho/errado/contatos.txt");

        List<Contato> contatos = List.of(
                new Contato("João", "+351912345678", "joao@email.com")
        );

        assertThrows(IOException.class, () -> {
            repoComFicheiroInexistente.salvar(contatos);
        });
    }

    @Test
    void testCarregarContatosComFicheiroInexistente() {
        FicheiroContatos repoInexistente = new FicheiroContatos("/caminho/errado/contatos.txt");
        assertDoesNotThrow(() -> {
            List<Contato> contatosCarregados = repoInexistente.carregar();
            assertTrue(contatosCarregados.isEmpty(), "A lista de contatos carregados deve estar vazia.");
        });
    }

    @Test
    void testGuardarContatos() throws IOException {
        List<Contato> contatosOriginais = List.of(
                new Contato("Carlos", "+351934567890", "carlos@email.com")
        );
        FicheiroContatos.guardarContatos(contatosOriginais);
        List<Contato> carregados = FicheiroContatos.carregarContatos();
        assertEquals(1, carregados.size());
        assertEquals("Carlos", carregados.get(0).getNome());
        assertEquals("+351934567890", carregados.get(0).getTelefone());
        assertEquals("carlos@email.com", carregados.get(0).getEmail());
    }
}