package AgendaContactos;

import javax.swing.SwingUtilities;

/**
 * Classe principal que inicia a aplicação da agenda de contactos.
 * 
 * A classe {@code Main} serve como ponto de entrada da aplicação. Ela inicializa a interface gráfica (GUI) da aplicação
 * utilizando a classe {@link ContatoGui}. A execução da GUI é feita na thread de eventos do Swing através do método
 * {@link SwingUtilities#invokeLater}.
 */
public class Main {
    /**
     * Método principal que inicia a aplicação.
     * 
     * @param args Argumentos passados pela linha de comandos. Não são utilizados neste caso.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContatoGui::new);
    }
}
