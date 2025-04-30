//package AgendaContactos;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//public class GestorContatosTest {
//
//    @Test
//    void testAdicionarContato() {
//
//        ContatoRepository mockRepo = Mockito.mock(ContatoRepository.class);
//
//        GestorContatos gestor = new GestorContatos(mockRepo);
//
//
//        Contato contato = new Contato("Alice", "12345", "alice@email.com");
//
//
//        gestor.adicionarContato(contato);
//
//
//        Mockito.verify(mockRepo).adicionar(contato);
//    }
//
//}