package AgendaContactos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



import java.util.Stack;

/**
 * Classe responsável pela gestão de contactos. Permite adicionar, editar, pesquisar e ordenar contactos.
 * Utiliza um repositório para salvar e carregar os dados, sendo este repositório uma implementação da interface {@link ContatoRepository}.
 */
public class GestorContatos {

    private List<ObservadorContatos> observadores = new ArrayList<>();

    /** Lista de contactos geridos pelo gestor. */
    private List<Contato> contatos;
    /** Repositório para carregar e salvar os contactos. */
    private ContatoRepository repository;

    private Stack<ContatoMemento> historico = new Stack<>();
    /**
     * Construtor da classe GestorContatos. Inicializa o gestor com o repositório fornecido e carrega os contactos existentes.
     * 
     * @param repository Repositório utilizado para salvar e carregar os contactos.
     * @throws IOException Caso ocorra um erro ao carregar os contactos do repositório.
     */
    public GestorContatos(ContatoRepository repository) throws IOException {
        this.repository = repository;
        this.contatos = repository.carregar();
    }

 
    /**
     * Adiciona um novo contacto à lista e salva no repositório.
     * 
     * @param contato O contacto a ser adicionado.
     * @throws IOException Caso ocorra um erro ao salvar os contactos no repositório.
     */
    public void adicionarContato(Contato contato) throws IOException {
        contatos.add(contato);
        repository.salvar(contatos);
        notificar();

        
    }

   
    /**
     * Edita os dados de um contacto existente.
     * 
     * @param nomeAntigo O nome do contacto a ser editado.
     * @param novoNome O novo nome do contacto.
     * @param novoTelefone O novo telefone do contacto.
     * @param novoEmail O novo email do contacto.
     * @throws IOException Caso ocorra um erro ao salvar os contactos no repositório.
     */
    public Contato buscarContatoPorNome(String nome) {
    for (Contato c : contatos) {
        if (c.getNome().equalsIgnoreCase(nome)) {
            return c;
        }
    }
    return null; // não encontrou
}

    public void editarContato(String nomeAntigo, String novoNome, String novoTelefone, String novoEmail) throws IOException {
        Contato contatoOriginal = buscarContatoPorNome(nomeAntigo);
        if(contatoOriginal != null){
            historico.push(new ContatoMemento(contatoOriginal));

            contatoOriginal.setNome(novoNome);
            contatoOriginal.setTelefone(novoTelefone);
            contatoOriginal.setEmail(novoEmail);
            repository.salvar(contatos);
            notificar();
        }

    }

public boolean desfazerAcao() throws IOException {
    if (historico.isEmpty()) {
        return false; // Nada para desfazer
    }

    ContatoMemento memento = historico.pop();
    Contato estadoAntigo = memento.restaurar();

    Contato contatoAtual = null;

    for (Contato c : contatos) {
        if (c.getEmail().equals(estadoAntigo.getEmail())) {
            contatoAtual = c;
            break;
        }
    }

    if (contatoAtual != null) {
        contatoAtual.setNome(estadoAntigo.getNome());
        contatoAtual.setTelefone(estadoAntigo.getTelefone());
        contatoAtual.setEmail(estadoAntigo.getEmail());
    } else {
        contatos.add(estadoAntigo);
    }

    repository.salvar(contatos);
    return true;
}





  
    /**
     * Pesquisa contactos que contenham o termo fornecido no nome, telefone ou email.
     * 
     * @param termo O termo de pesquisa a ser procurado nos campos nome, telefone ou email.
     * @return Lista de contactos que correspondem ao termo de pesquisa.
     */
    public List<Contato> pesquisarContato(String termo) {
        return contatos.stream()
                .filter(c -> c.getNome().toLowerCase().contains(termo.toLowerCase()) ||
                             c.getTelefone().contains(termo) ||
                             c.getEmail().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }

   
    /**
     * Retorna a lista de todos os contactos geridos.
     * 
     * @return Lista de todos os contactos.
     */
    public List<Contato> getTodosContatos() {
        return new ArrayList<>(contatos);
    }


    /**
     * Ordena os contactos por nome, podendo ser em ordem crescente ou decrescente.
     * 
     * @param crescente Se for {@code true}, a ordenação será crescente. Caso contrário, será decrescente.
     */
    public void ordenarPorNome(boolean crescente) {
        contatos.sort((c1, c2) -> crescente ?
                c1.getNome().compareToIgnoreCase(c2.getNome()) :
                c2.getNome().compareToIgnoreCase(c1.getNome()));
    }
    
    /**
     * Ordena os contactos por email, podendo ser em ordem crescente ou decrescente.
     * 
     * @param crescente Se for {@code true}, a ordenação será crescente. Caso contrário, será decrescente.
     */
    public void ordenarPorEmail(boolean crescente) {
        contatos.sort((c1, c2) -> crescente ?
                c1.getEmail().compareToIgnoreCase(c2.getEmail()) :
                c2.getEmail().compareToIgnoreCase(c1.getEmail()));
    }
    
    



    

public void adicionarObservador(ObservadorContatos obs) {
    observadores.add(obs);
}

private void notificar() {
    for (ObservadorContatos obs : observadores) {
        obs.atualizar(new ArrayList<>(contatos));  // passar cópia para evitar alterações externas
    }
}

}

