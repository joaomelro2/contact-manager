package AgendaContactos;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class FicheiroContatos implements ContatoRepository {

    private static String caminhoFicheiro;
    
    public FicheiroContatos(){
        caminhoFicheiro = "contatos.txt";
    }

    public FicheiroContatos(String path){
        caminhoFicheiro = path;
    }

    @Override
    public void salvar(List<Contato> contatos) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoFicheiro))) {
            for (Contato contato : contatos) {
                writer.write(contato.toString());
                writer.newLine();
            }
        }
    }

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

