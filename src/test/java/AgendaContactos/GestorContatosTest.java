package AgendaContactos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestorContatosTest {

    private GestorContatos gestor;

    @Mock
    private ContatoRepository repositorio;

    private Contato meuContacto;
    private Contato meuContacto2;

    @BeforeEach
    void setUp() throws IOException {
        when(repositorio.carregar()).thenReturn(new ArrayList<>());
        gestor = new GestorContatos(repositorio);
        meuContacto = new Contato("João", "123456789", "meu@email.com");
        meuContacto2 = new Contato("Sara", "987654321", "sara@email.com");
    }

    @Test
    void adicionarContato() throws IOException {
        gestor.adicionarContato(meuContacto);
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
        verify(repositorio).salvar(anyList());
    }

    @Test
    void editarContato() throws IOException {
        gestor.adicionarContato(meuContacto);
        gestor.editarContato("João", "Sara", "987654321", "novo@email.com");
        assertEquals("Sara,987654321,novo@email.com", gestor.getTodosContatos().get(0).toString());
        verify(repositorio, times(2)).salvar(anyList());
    }

    @Test
    void pesquisarContato() throws IOException {
        gestor.adicionarContato(meuContacto);
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("ão").toString());
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("jo").toString());
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("789").toString());
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("meu").toString());
        assertEquals("[João,123456789,meu@email.com]", gestor.pesquisarContato("meu@email").toString());
    }

    @Test
    void getTodosContatos() throws IOException {
        assertEquals(new ArrayList<>(), gestor.getTodosContatos());
        gestor.adicionarContato(meuContacto);
        assertEquals(1, gestor.getTodosContatos().size());
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
    }

    @Test
    void ordenarPorNome_true() throws IOException {
        gestor.adicionarContato(meuContacto2);
        gestor.adicionarContato(meuContacto);
        gestor.ordenarPorNome(true);
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
        assertEquals("Sara,987654321,sara@email.com", gestor.getTodosContatos().get(1).toString());
    }

    @Test
    void ordenarPorNome_false() throws IOException {
        gestor.adicionarContato(meuContacto);
        gestor.adicionarContato(meuContacto2);
        gestor.ordenarPorNome(false);
        assertEquals("Sara,987654321,sara@email.com", gestor.getTodosContatos().get(0).toString());
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(1).toString());
    }

    @Test
    void ordenarPorEmail_true() throws IOException {
        gestor.adicionarContato(meuContacto2);
        gestor.adicionarContato(meuContacto);
        gestor.ordenarPorEmail(true);
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(0).toString());
        assertEquals("Sara,987654321,sara@email.com", gestor.getTodosContatos().get(1).toString());
    }

    @Test
    void ordenarPorEmail_false() throws IOException {
        gestor.adicionarContato(meuContacto);
        gestor.adicionarContato(meuContacto2);
        gestor.ordenarPorEmail(false);
        assertEquals("Sara,987654321,sara@email.com", gestor.getTodosContatos().get(0).toString());
        assertEquals("João,123456789,meu@email.com", gestor.getTodosContatos().get(1).toString());
    }
}
