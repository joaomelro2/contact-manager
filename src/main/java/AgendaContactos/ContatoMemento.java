package AgendaContactos;

public class ContatoMemento {
    private final Contato backup;

    public ContatoMemento(Contato original) {
        this.backup = new Contato(
            original.getNome(), 
            original.getTelefone(), 
            original.getEmail()
        );
    }

    public Contato restaurar(){
        return new Contato(
            backup.getNome(),
            backup.getTelefone(),
            backup.getEmail()
        );
    }
}
