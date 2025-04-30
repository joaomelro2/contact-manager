package AgendaContactos;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que implementa a interface {@link ContatoRepository} para o acesso aos dados de contactos a partir de um ficheiro de texto.
 * Esta classe permite salvar e carregar a lista de contactos a partir de um ficheiro localizado em {@link FicheiroContatos#CAMINHO_FICHEIRO}.
 */
public class FicheiroContatos implements ContatoRepository {

    /** Caminho do ficheiro onde os contactos são armazenados. */
    private static String caminhoFicheiro;
    
    public FicheiroContatos(){
        caminhoFicheiro = "contatos.txt";
    }

    public FicheiroContatos(String path){
        caminhoFicheiro = path;
    }

    /**
     * Método que salva a lista de contactos no ficheiro.
     * Cada contacto é salvo em uma linha no formato "nome,telefone,email".
     * 
     * @param contatos Lista de contactos a ser salva no ficheiro.
     * @throws IOException Caso ocorra um erro durante o processo de gravação.
     */
    @Override
    public void salvar(List<Contato> contatos) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoFicheiro))) {
            for (Contato contato : contatos) {
                writer.write(contato.toString());
                writer.newLine();
            }
        }
    }

    /**
     * Método que carrega a lista de contactos a partir do ficheiro.
     * Cada linha do ficheiro é dividida por vírgula para obter as informações de cada contacto.
     * 
     * @return Lista de contactos carregada a partir do ficheiro.
     * @throws IOException Caso ocorra um erro durante o processo de leitura.
     */
    @Override
    public List<Contato> carregar() throws IOException {
        List<Contato> contatos = new ArrayList<>();
        File file = new File(caminhoFicheiro);
        if(!file.exists()) {
            return contatos;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(",");
                if (dados.length == 3) {
                    Contato contato = new Contato(dados[0], dados[1], dados[2]);
                    contatos.add(contato);
                }
            }
        }
        return contatos;
    }

    /**
     * Método estático que salva a lista de contactos no ficheiro.
     * 
     * @param contatos Lista de contactos a ser salva no ficheiro.
     */
    public static void guardarContatos(List<Contato> contatos) {
        try(BufferedWriter writer = new BufferedWriter( new FileWriter(caminhoFicheiro))){
            for (Contato c : contatos){
                writer.write(c.getNome() + "," + c.getTelefone() + "," + c.getEmail());
                writer.newLine();
            }
        }catch (IOException e) {
                System.out.println("Erro ao guardar os contatos: " + e.getMessage());
            }
        }
    
     /**
     * Método estático que carrega a lista de contactos a partir do ficheiro.
     * 
     * @return Lista de contactos carregada do ficheiro.
     */
    public static List<Contato> carregarContatos() {
        List<Contato> contatos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoFicheiro))) {
            String linha;
            while((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                   contatos.add(new Contato(dados[0], dados[1], dados[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os contatos: " + e.getMessage());
        }
        return contatos;
    }

}

