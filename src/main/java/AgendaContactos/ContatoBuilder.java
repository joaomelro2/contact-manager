package AgendaContactos;

public class ContatoBuilder {
    private String nome, telefone, email;

    public ContatoBuilder comNome(String nome) { this.nome = nome; return this; }
    public ContatoBuilder comTelefone(String telefone) { this.telefone = telefone; return this; }
    public ContatoBuilder comEmail(String email) { this.email = email; return this; }

    public Contato construir(){
        return new Contato(nome, telefone, email);
    }
}
