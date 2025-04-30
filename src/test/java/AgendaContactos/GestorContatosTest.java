package AgendaContactos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Anotação para carregar os mocks automaticamente
class GestorContatosTest {

    private GestorContatos gestor;

    @Mock
    private ContatoRepository repositorio;

    private Contato meuContato;
    private Contato meuContato2;

    @BeforeEach
    void setUp() throws IOException {
        when(repositorio.carregar()).thenReturn(new ArrayList<>());
        gestor = new GestorContatos(repositorio);
        meuContato = new Contato("João", "123456789", "meu@email.com");
        meuContato2 = new Contato("Sara", "987654321", "sara@email.com");
    }

    @Test
    void adicionarContato() throws IOException {
        gestor.adicionarContato(meuContato);
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
        verify(repositorio).salvar(anyList()); // Verifica se salvar foi chamado
    }

    @Test
    void editarContato() throws IOException {
        gestor.adicionarContato(meuContato);
        gestor.editarContato("João", "Sara", "987654321", "novo@email.com");
        assertEquals("Sara,987654321,novo@email.com", gestor.getTodosContatos().get(0).toString());
        verify(repositorio, times(2)).salvar(anyList()); // Verifica se salvar foi chamado duas vezes
    }

    @Test
    void pesquisarContatoPorNome() throws IOException {
        gestor.adicionarContato(meuContato);
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("ão").toString());
    }

    @Test
    void pesquisarContatoPorTelefone() throws IOException {
        gestor.adicionarContato(meuContato);
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("123456789").toString());
    }

    @Test
    void pesquisarContatoPorEmail() throws IOException {
        gestor.adicionarContato(meuContato);
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("meu@email.com").toString());
    }

    @Test
    void getTodosContatosVazio() throws IOException {
        assertEquals(new ArrayList<>(), gestor.getTodosContatos());
    }

    @Test
    void getTodosContatosNaoVazio() throws IOException {
        gestor.adicionarContato(meuContato);
        assertEquals(1, gestor.getTodosContatos().size());
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
    }

    @Test
    void ordenarPorNomeCrescente() throws IOException {
        gestor.adicionarContato(meuContato2);
        gestor.adicionarContato(meuContato);
        gestor.ordenarPorNome(true);
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
    }

    @Test
    void ordenarPorNomeDecrescente() throws IOException {
        gestor.adicionarContato(meuContato);
        gestor.adicionarContato(meuContato2);
        gestor.ordenarPorNome(false);
        assertEquals("Sara,987654321,sara@email.com", gestor.getTodosContatos().get(0).toString());
    }

    @Test
    void ordenarPorEmailCrescente() throws IOException {
        gestor.adicionarContato(meuContato2);
        gestor.adicionarContato(meuContato);
        gestor.ordenarPorEmail(true);
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
    }

    @Test
    void ordenarPorEmailDecrescente() throws IOException {
        gestor.adicionarContato(meuContato);
        gestor.adicionarContato(meuContato2);
        gestor.ordenarPorEmail(false);
        assertEquals("Sara,987654321,sara@email.com", gestor.getTodosContatos().get(0).toString());
    }

    @Test
    void pesquisarComStringVazia() throws IOException {
        gestor.adicionarContato(meuContato);
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("").toString());
    }

    @Test
    void editarContatoComDadosIguais() throws IOException {
        gestor.adicionarContato(meuContato);
        gestor.editarContato("João", "João", "123456789", "meu@email.com");
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
    }

    @Test
    void contatosComMesmoNome() throws IOException {
        Contato contato1 = new Contato("Luis", "123", "luis1@email.com");
        Contato contato2 = new Contato("Luis", "456", "luis2@email.com");
        gestor.adicionarContato(contato1);
        gestor.adicionarContato(contato2);
        assertEquals(2, gestor.pesquisarContato("Luis").size());
    }

    @Test
    void getTodosContatos() throws IOException {
        gestor.adicionarContato(meuContato);
        gestor.getTodosContatos();
        verify(repositorio).salvar(anyList());
    }

    @Test
    void adicionarContatoComDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.adicionarContato(new Contato("", "", ""));
        });
    }
}
