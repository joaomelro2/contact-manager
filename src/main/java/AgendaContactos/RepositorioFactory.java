package AgendaContactos;
public class RepositorioFactory {
    public static ContatoRepository criarRepositorio() {
        return FicheiroContatos.getInstance();
    }
    
}
