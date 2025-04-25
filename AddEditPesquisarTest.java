import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AddEditPesquisarTest {

    private AddEditPesquisar novaPesquisa;
    private Contato meuContacto;
    private Contato meuContacto2;

    @BeforeEach
    void setUp() {
        novaPesquisa = new AddEditPesquisar();
        meuContacto = new Contato("João", "123456789", "meu@email.com");
        meuContacto2 = new Contato("Sara", "987654321", "sara@email.com");
    }

    @Test
    void adicionarContato() {
        novaPesquisa.adicionarContato(meuContacto);
        assertEquals("João,123456789,meu@email.com", novaPesquisa.getContatos().getFirst().toString());
    }

    @Test
    void editarContato() {
        novaPesquisa.adicionarContato(meuContacto);
        novaPesquisa.editarContato("João", "Sara", "987654321", "novo@email.com");
        assertEquals("Sara,987654321,novo@email.com", novaPesquisa.getContatos().getFirst().toString());
    }

    @Test
    void pesquisarContato() {
        novaPesquisa.adicionarContato(meuContacto);
        assertEquals("[João,123456789,meu@email.com]", novaPesquisa.pesquisarContato("ão").toString());
        assertEquals("[João,123456789,meu@email.com]", novaPesquisa.pesquisarContato("jo").toString());
        assertEquals("[João,123456789,meu@email.com]", novaPesquisa.pesquisarContato("789").toString());
        assertEquals("[João,123456789,meu@email.com]", novaPesquisa.pesquisarContato("meu").toString());
        assertEquals("[João,123456789,meu@email.com]", novaPesquisa.pesquisarContato("meu@email").toString());
    }

    @Test
    void ordenarPorNome_true() {
        novaPesquisa.adicionarContato(meuContacto2);
        novaPesquisa.adicionarContato(meuContacto);
        novaPesquisa.ordenarPorNome(true);

        assertEquals("João,123456789,meu@email.com", novaPesquisa.getContatos().getFirst().toString());
        assertEquals("Sara,987654321,sara@email.com", novaPesquisa.getContatos().get(1).toString());
    }

    @Test
    void ordenarPorNome_false() {
        novaPesquisa.adicionarContato(meuContacto);
        novaPesquisa.adicionarContato(meuContacto2);

        novaPesquisa.ordenarPorNome(false);
        assertEquals("Sara,987654321,sara@email.com", novaPesquisa.getContatos().getFirst().toString());
        assertEquals("João,123456789,meu@email.com", novaPesquisa.getContatos().get(1).toString());
    }

    @Test
    void ordenarPorEmail_true() {
        novaPesquisa.adicionarContato(meuContacto2);
        novaPesquisa.adicionarContato(meuContacto);
        novaPesquisa.ordenarPorNome(true);

        assertEquals("João,123456789,meu@email.com", novaPesquisa.getContatos().getFirst().toString());
        assertEquals("Sara,987654321,sara@email.com", novaPesquisa.getContatos().get(1).toString());
    }

    @Test
    void ordenarPorEmail_false() {
        novaPesquisa.adicionarContato(meuContacto);
        novaPesquisa.adicionarContato(meuContacto2);

        novaPesquisa.ordenarPorNome(false);
        assertEquals("Sara,987654321,sara@email.com", novaPesquisa.getContatos().getFirst().toString());
        assertEquals("João,123456789,meu@email.com", novaPesquisa.getContatos().get(1).toString());
    }

    @Test
    void getContatos() {
        assertEquals(new ArrayList<>(), novaPesquisa.getContatos());
        novaPesquisa.adicionarContato(meuContacto);
        assertEquals("João,123456789,meu@email.com", novaPesquisa.getContatos().getFirst().toString());
    }
}