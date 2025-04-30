package AgendaContactos;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class GestorContatosTest2 {
   private ContatoRepository contatoRepository;
    private GestorContatos gestorContatos;

    @BeforeEach
    void setUp() throws IOException {
        contatoRepository = Mockito.mock(ContatoRepository.class);
        when(contatoRepository.carregar()).thenReturn(new ArrayList<>());
        gestorContatos = new GestorContatos(contatoRepository);
    }

    @Test
    void testAdicionarContato() throws IOException {
        Contato c = new Contato("Ana", "111", "ana@email.com");
        gestorContatos.adicionarContato(c);
        assertEquals(1, gestorContatos.getTodosContatos().size());
    }

    @Test
    void testEditarContato() throws IOException {
        Contato c = new Contato("Ana", "111", "ana@email.com");
        List<Contato> lista = new ArrayList<>();
        lista.add(c);
        when(contatoRepository.carregar()).thenReturn(lista);
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.editarContato("Ana", "Ana Maria", "222", "ana.maria@email.com");
        Contato atualizado = gestorContatos.getTodosContatos().get(0);
        assertEquals("Ana Maria", atualizado.getNome());
        assertEquals("222", atualizado.getTelefone());
        assertEquals("ana.maria@email.com", atualizado.getEmail());
    }

    @Test
    void testPesquisarPorNome() throws IOException {
        Contato c = new Contato("Bruno", "222", "bruno@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("bru");
        assertEquals(1, resultado.size());
    }

    @Test
    void testPesquisarPorTelefone() throws IOException {
        Contato c = new Contato("Carlos", "333", "carlos@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("333");
        assertEquals(1, resultado.size());
    }

    @Test
    void testPesquisarPorEmail() throws IOException {
        Contato c = new Contato("Diana", "444", "diana@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("diana@");
        assertEquals(1, resultado.size());
    }

    @Test
    void testPesquisarInexistente() throws IOException {
        Contato c = new Contato("Eduardo", "555", "eduardo@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("zzz");
        assertEquals(0, resultado.size());
    }

    @Test
    void testGetTodosContatos() {
        assertEquals(0, gestorContatos.getTodosContatos().size());
    }

    @Test
    void testOrdenarPorNomeCrescente() throws IOException {
        Contato a = new Contato("Zara", "777", "zara@email.com");
        Contato b = new Contato("Andre", "888", "andre@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorNome(true);
        assertEquals("Andre", gestorContatos.getTodosContatos().get(0).getNome());
    }

    @Test
    void testOrdenarPorNomeDecrescente() throws IOException {
        Contato a = new Contato("Zara", "777", "zara@email.com");
        Contato b = new Contato("Andre", "888", "andre@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorNome(false);
        assertEquals("Zara", gestorContatos.getTodosContatos().get(0).getNome());
    }

    @Test
    void testOrdenarPorEmailCrescente() throws IOException {
        Contato a = new Contato("Hugo", "999", "hugo@email.com");
        Contato b = new Contato("Ines", "000", "ana@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorEmail(true);
        assertEquals("ana@email.com", gestorContatos.getTodosContatos().get(0).getEmail());
    }

    @Test
    void testOrdenarPorEmailDecrescente() throws IOException {
        Contato a = new Contato("Hugo", "999", "hugo@email.com");
        Contato b = new Contato("Ines", "000", "ana@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorEmail(false);
        assertEquals("hugo@email.com", gestorContatos.getTodosContatos().get(0).getEmail());
    }
    @Test
    void testPesquisarComStringVazia() throws IOException {
        Contato c = new Contato("Joao", "444", "joao@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("");
        assertEquals(1, resultado.size());
    }

    @Test
    void testGetTodosContatosVazio() {
        List<Contato> contatos = gestorContatos.getTodosContatos();
        assertTrue(contatos.isEmpty());
    }

    @Test
    void testGetTodosContatosNaoVazio() throws IOException {
        Contato c = new Contato("Maria", "555", "maria@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> contatos = gestorContatos.getTodosContatos();
        assertFalse(contatos.isEmpty());
    }

    @Test
    void testEditarContatoComDadosIguais() throws IOException {
        Contato c = new Contato("Miguel", "123", "miguel@email.com");
        List<Contato> lista = new ArrayList<>();
        lista.add(c);
        when(contatoRepository.carregar()).thenReturn(lista);
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.editarContato("Miguel", "Miguel", "123", "miguel@email.com");
        assertEquals("Miguel", gestorContatos.getTodosContatos().get(0).getNome());
    }

    @Test
    void testPesquisarPorParteDoEmail() throws IOException {
        Contato c = new Contato("Teresa", "123", "teresa@email.com");
        when(contatoRepository.carregar()).thenReturn(List.of(c));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("@email");
        assertEquals(1, resultado.size());
    }

    @Test
    void testOrdenarListaVazia() throws IOException {
        when(contatoRepository.carregar()).thenReturn(new ArrayList<>());
        gestorContatos = new GestorContatos(contatoRepository);
        gestorContatos.ordenarPorNome(true);
        assertEquals(0, gestorContatos.getTodosContatos().size());
    }

    @Test
    void testContatosComMesmoNome() throws IOException {
        Contato a = new Contato("Luis", "123", "luis1@email.com");
        Contato b = new Contato("Luis", "456", "luis2@email.com");
        when(contatoRepository.carregar()).thenReturn(Arrays.asList(a, b));
        gestorContatos = new GestorContatos(contatoRepository);
        List<Contato> resultado = gestorContatos.pesquisarContato("Luis");
        assertEquals(2, resultado.size());
    }
}

    
