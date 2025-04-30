package AgendaContactos;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class GestorContatosTest2 {

    @Test
    void testAdicionarContato() throws IOException {
        ContatoRepository mockRepo = Mockito.mock(ContatoRepository.class);
        GestorContatos gestor = new GestorContatos(mockRepo);

        Contato contato = new Contato("Alice", "12345", "alice@example.com");
        gestor.adicionarContato(contato);

        ArgumentCaptor<List<Contato>> captor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(mockRepo).salvar(captor.capture());

        assertEquals("Alice", captor.getValue().get(0).getNome());
    }
    
}