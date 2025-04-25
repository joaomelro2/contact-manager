import java.util.*;
import java.util.stream.Collectors;

public class AddEditPesquisar {
    private List<Contato> contatos = new ArrayList<>();

    public void adicionarContato(Contato contato) {
        contatos.add(contato);
    }

    public void editarContato(String nomeAntigo, String novoNome, String noveTelefone, String novoEmail)
    {
        for (Contato c : contatos) {
            // Encontra o contato com o nome antigo
            if (c.getNome().equalsIgnoreCase(nomeAntigo)) {
                c.setNome(novoNome);
                c.setTelefone(noveTelefone);
                c.setEmail(novoEmail);
                break;
            }

        }
    } 
    // PESQUISA
    public List<Contato> pesquisarContato(String termo) {
        return contatos.stream()
        .filter(c -> c.getNome().toLowerCase().contains(termo.toLowerCase()) ||
                     c.getTelefone().contains(termo) ||
                     c.getEmail().toLowerCase().contains(termo))
        .collect(Collectors.toList());
    }
    
    // ORDENAÇÃO
    public void ordenarPorNome(boolean crescente){
        contatos.sort(Comparator.comparing(Contato::getNome));
        if(!crescente) Collections.reverse(contatos);
    }

    public void ordenarPorEmail(boolean crescente){
        contatos.sort(Comparator.comparing(Contato::getEmail));
        if(!crescente) Collections.reverse(contatos);
    }

    public List<Contato> getContatos() {
        return contatos;
    }
    
}
