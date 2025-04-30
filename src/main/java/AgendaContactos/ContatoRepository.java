package AgendaContactos;

import java.io.IOException;
import java.util.List;

/**
 * Interface que define os métodos para acesso aos dados de contactos.
 * Esta interface é responsável por salvar e carregar a lista de contactos.
 * Pode ser implementada por diferentes fontes de dados, como ficheiros ou bases de dados.
 */
public interface ContatoRepository {
   
     /**
     * Método para salvar a lista de contactos.
     * 
     * @param contatos Lista de contactos a ser salva.
     * @throws IOException Caso ocorra um erro durante o processo de gravação.
     */
    void salvar(List<Contato> contatos) throws IOException;
 
    /**
     * Método para carregar a lista de contactos.
     * 
     * @return Lista de contactos carregada.
     * @throws IOException Caso ocorra um erro durante o processo de leitura.
     */
     List<Contato> carregar() throws IOException;
}
