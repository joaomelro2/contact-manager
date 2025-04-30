package AgendaContactos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContatoTest {

    private Contato meuContacto;

    @BeforeEach
    void setUp() {
        meuContacto = new Contato("João", "123456789", "meu@email.com");
        System.out.println("Inicio do teste");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Fim do teste.");
    }

    @Test
    void getNome() {
        assertEquals("João", meuContacto.getNome());
        System.out.println(" - Teste getNome() executado com sucesso");
    }

    @Test
    void setNome() {
        meuContacto.setNome("Sara");
        assertEquals("Sara", meuContacto.getNome());
        System.out.println(" - Teste setNome() executado com sucesso");
    }

    @Test
    void getTelefone() {
        assertEquals("123456789", meuContacto.getTelefone());
        System.out.println(" - Teste getTelefone() executado com sucesso");
    }

    @Test
    void setTelefone() {
        meuContacto.setTelefone("987654321");
        assertEquals("987654321", meuContacto.getTelefone());
        System.out.println(" - Teste setTelefone() executado com sucesso");

    }

    @Test
    void getEmail() {
        assertEquals("meu@email.com", meuContacto.getEmail());
        System.out.println(" - Teste getEmail() executado com sucesso");

    }

    @Test
    void setEmail() {
        meuContacto.setEmail("sara@email.com");
        assertEquals("sara@email.com", meuContacto.getEmail());
        System.out.println(" - Teste setEmail() executado com sucesso");

    }

    @Test
    void testToString() {
        assertEquals("João,123456789,meu@email.com", meuContacto.toString());
        System.out.println(" - Teste testToString() executado com sucesso");

    }
}