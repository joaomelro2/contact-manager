package AgendaContactos;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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

  @Test
    void testAdicionarVariosContatos() throws IOException {
        ContatoRepository mockRepo = mock(ContatoRepository.class);
        GestorContatos gestor = new GestorContatos(mockRepo);

        Contato contato1 = new Contato("Alice", "12345", "alice@example.com");
        Contato contato2 = new Contato("Bob", "67890", "bob@example.com");


        gestor.adicionarContato(contato1);
        gestor.adicionarContato(contato2);

    
        verify(mockRepo, times(2)).salvar(anyList());

  
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(mockRepo, times(2)).salvar(captor.capture());

    
        List<Contato> contatosSalvos = captor.getValue();
        assertEquals(2, contatosSalvos.size());
        assertEquals("Alice", contatosSalvos.get(0).getNome());
        assertEquals("Bob", contatosSalvos.get(1).getNome());
}


    
}