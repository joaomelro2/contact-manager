package AgendaContactos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class GestorContatosTest {
    private ContatoRepository contatoRepository;
    private GestorContatos gestorContatos;
    private Contato c1, c2, c3;

    @BeforeEach
    void setUp() throws IOException {
        contatoRepository = mock(ContatoRepository.class);
        when(contatoRepository.carregar()).thenReturn(new ArrayList<>());
        gestorContatos = new GestorContatos(contatoRepository);

        c1 = new Contato("Bruno", "222", "bruno@email.com");
        c2 = new Contato("Carlos", "333", "carlos@email.com");
        c3 = new Contato("Diana", "444", "diana@email.com");
    }

    @Test
    void adicionarContato() throws IOException {
        gestorContatos.adicionarContato(c1);
        assertEquals(1, gestorContatos.getTodosContatos().size());
    }

    @Test
    void editarContato() throws IOException {
        List<Contato> lista = new ArrayList<>();
        lista.add(c1);
        when(contatoRepository.carregar()).thenReturn(lista);
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.editarContato("Bruno", "Bruno Silva", "555", "bruno.silva@email.com");
        Contato atualizado = gestorContatos.getTodosContatos().get(0);
        assertEquals("Bruno Silva", atualizado.getNome());
        assertEquals("555", atualizado.getTelefone());
        assertEquals("bruno.silva@email.com", atualizado.getEmail());
    }

    @Test
    void pesquisarPorNome() throws IOException {
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(c1, c2, c3));
        gestorContatos = new GestorContatos(contatoRepository);

        List<Contato> resultadoNome = gestorContatos.pesquisarContato("bru");
        assertEquals(1, resultadoNome.size());
        assertEquals("Bruno", resultadoNome.get(0).getNome());
    }

    @Test
    void pesquisarPorTelefone() throws IOException {
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(c1, c2, c3));
        gestorContatos = new GestorContatos(contatoRepository);

        List<Contato> resultadoTelefone = gestorContatos.pesquisarContato("333");
        assertEquals(1, resultadoTelefone.size());
        assertEquals("Carlos", resultadoTelefone.get(0).getNome());
    }

    @Test
    void pesquisarPorEmail() throws IOException {
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(c1, c2, c3));
        gestorContatos = new GestorContatos(contatoRepository);

        List<Contato> resultadoEmail = gestorContatos.pesquisarContato("diana@");
        assertEquals(1, resultadoEmail.size());
        assertEquals("Diana", resultadoEmail.get(0).getNome());
    }

    @Test
    void pesquisarInexistente() throws IOException {
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(c1, c2, c3));
        gestorContatos = new GestorContatos(contatoRepository);

        List<Contato> resultadoInexistente = gestorContatos.pesquisarContato("zzz");
        assertEquals(0, resultadoInexistente.size());
    }

    @Test
    void getTodosContatosVazio() {
        List<Contato> contatos = gestorContatos.getTodosContatos();
        assertTrue(contatos.isEmpty());
    }

    @Test
    void ordenarPorNomeCrescente() throws IOException {
        Contato a = new Contato("Zara", "777", "zara@email.com");
        Contato b = new Contato("Andre", "888", "andre@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorNome(true);
        assertEquals("Andre", gestorContatos.getTodosContatos().get(0).getNome());
    }

    @Test
    void ordenarPorNomeDecrescente() throws IOException {
        Contato a = new Contato("Zara", "777", "zara@email.com");
        Contato b = new Contato("Andre", "888", "andre@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorNome(false);
        assertEquals("Zara", gestorContatos.getTodosContatos().get(0).getNome());
    }

    @Test
    void ordenarPorEmailCrescente() throws IOException {
        Contato a = new Contato("Hugo", "999", "hugo@email.com");
        Contato b = new Contato("Ines", "000", "ana@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorEmail(true);
        assertEquals("ana@email.com", gestorContatos.getTodosContatos().get(0).getEmail());
    }

    @Test
    void ordenarPorEmailDecrescente() throws IOException {
        Contato a = new Contato("Hugo", "999", "hugo@email.com");
        Contato b = new Contato("Ines", "000", "ana@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorEmail(false);
        assertEquals("hugo@email.com", gestorContatos.getTodosContatos().get(0).getEmail());
    }

    @Test
    void adicionarContatoComDadosDuplicados() throws IOException {
        Contato c2 = new Contato("Ana", "111", "ana@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(c1));
        gestorContatos.adicionarContato(c2);
        assertEquals(1, gestorContatos.getTodosContatos().size());  // Verifica que o contato duplicado não foi adicionado
    }

    @Test
    void pesquisarComStringVazia() throws IOException {
        Contato c = new Contato("Joao", "444", "joao@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("");
        assertEquals(1, resultado.size());
    }

    @Test
    void ordenarListaVazia() throws IOException {
        when(contatoRepository.carregar()).thenReturn(new ArrayList<>());
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorNome(true);
        assertEquals(0, gestorContatos.getTodosContatos().size());
    }

    @Test
    void contatosComMesmoNome() throws IOException {
        Contato a = new Contato("Luis", "123", "luis1@email.com");
        Contato b = new Contato("Luis", "456", "luis2@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("Luis");
        assertEquals(2, resultado.size());
    }

    @Test
    void editarContatoComDadosIguais() throws IOException {
        List<Contato> lista = new ArrayList<>();
        lista.add(c1);
        when(contatoRepository.carregar()).thenReturn(lista);
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.editarContato("Bruno", "Bruno", "222", "bruno@email.com");
        assertEquals("Bruno", gestorContatos.getTodosContatos().get(0).getNome());
    }

    @Test
    void editarContatoComDadosVazios() throws IOException {
        List<Contato> lista = new ArrayList<>();
        lista.add(c1);
        when(contatoRepository.carregar()).thenReturn(lista);
        gestorContatos = new GestorContatos(contatoRepository);

        gestorContatos.editarContato("Bruno", "Bruno", "", "bruno.silva@email.com");
        assertEquals("", gestorContatos.getTodosContatos().get(0).getTelefone());

        gestorContatos.editarContato("Bruno", "Bruno", "911111", "");
        assertEquals("", gestorContatos.getTodosContatos().get(0).getEmail());

        gestorContatos.editarContato("Bruno", "", "555", "bruno.silva@email.com");
        assertEquals("", gestorContatos.getTodosContatos().get(0).getNome());
    }
}
