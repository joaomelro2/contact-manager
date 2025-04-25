import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class FicheiroContato {

    private static final String FICHEIRO = "contatos.txt";

    public static void salvarContatos(List<Contato> contatos) throws IOException {
        // Escreve os contatos no ficheiro
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHEIRO))) {
            
            for (Contato contato : contatos) {
                writer.write(contato.toString());
                writer.newLine();
            }
        }
    }

    public static List<Contato> carregarContatos() throws IOException {
        List<Contato> contatos = new ArrayList<>();
        File file = new File(FICHEIRO);
        // Se o ficheiro não existir, retorna a lista vazia
        if (!file.exists()) return contatos;

        // Lê o conteúdo do ficheiro
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO))) {
            String linha;
            // Lê cada linha do ficheiro
            while ((linha = reader.readLine()) != null){
                String[] dados = linha.split(",");
                if(dados.length == 3) {
                    // Adiciona um novo contato à lista
                    contatos.add(new Contato(dados[0], dados[1], dados[2]));
                }
        }
        }
        return contatos;
    }
}
