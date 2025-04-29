package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorContatos {

    private List<Contato> contatos;
    private ContatoRepository repository;

    
    public GestorContatos(ContatoRepository repository) throws IOException {
        this.repository = repository;
        this.contatos = repository.carregar();
    }

 
    public void adicionarContato(Contato contato) throws IOException {
        contatos.add(contato);
        repository.salvar(contatos);
    }

   
    public void editarContato(String nomeAntigo, String novoNome, String novoTelefone, String novoEmail) throws IOException {
        for (Contato c : contatos) {
            if (c.getNome().equalsIgnoreCase(nomeAntigo)) {
                c.setNome(novoNome);
                c.setTelefone(novoTelefone);
                c.setEmail(novoEmail);
                break;
            }
        }
        repository.salvar(contatos);
    }

  
    public List<Contato> pesquisarContato(String termo) {
        return contatos.stream()
                .filter(c -> c.getNome().toLowerCase().contains(termo.toLowerCase()) ||
                             c.getTelefone().contains(termo) ||
                             c.getEmail().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }

   
    public List<Contato> getTodosContatos() {
        return new ArrayList<>(contatos);
    }


    
    public void ordenarPorNome(boolean crescente) {
        contatos.sort((c1, c2) -> crescente ?
                c1.getNome().compareToIgnoreCase(c2.getNome()) :
                c2.getNome().compareToIgnoreCase(c1.getNome()));
    }
    
    public void ordenarPorEmail(boolean crescente) {
        contatos.sort((c1, c2) -> crescente ?
                c1.getEmail().compareToIgnoreCase(c2.getEmail()) :
                c2.getEmail().compareToIgnoreCase(c1.getEmail()));
    }
    
}

