package AgendaContactos;

import java.io.IOException;
import java.util.List;

/**
 * Interface para acesso aos dados de contactos.
 */
public interface ContatoRepository {
   
    void salvar(List<Contato> contatos) throws IOException;

    
    List<Contato> carregar() throws IOException;
}
