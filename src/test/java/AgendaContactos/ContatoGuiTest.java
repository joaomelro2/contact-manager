package AgendaContactos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContatoGuiTest {

    private ContatoGui gui;

    @BeforeEach
    void setUp() throws Exception {
        ContatoRepository mockRepo = mock(ContatoRepository.class);
        when(mockRepo.carregar()).thenReturn(new ArrayList<>());

        GestorContatos gestorMock = new GestorContatos(mockRepo);

        gui = new ContatoGui();

        Field field = ContatoGui.class.getDeclaredField("manipular");
        field.setAccessible(true);
        field.set(gui, gestorMock);
    }

    @Test
    void testValidarTelefoneValido() throws Exception {
        java.lang.reflect.Method metodo = ContatoGui.class.getDeclaredMethod("validarTelefone", String.class);
        metodo.setAccessible(true);
        boolean resultado = (boolean) metodo.invoke(gui, "+351912345678");
        assertTrue(resultado);
    }

    @Test
    void testValidarEmailValido() throws Exception {
        java.lang.reflect.Method metodo = ContatoGui.class.getDeclaredMethod("validarEmail", String.class);
        metodo.setAccessible(true);
        boolean resultado = (boolean) metodo.invoke(gui, "teste@email.com");
        assertTrue(resultado);
    }

    @Test
    void testInicializacaoGuiTitulo() {
        assertEquals("Agenda De Contatos", gui.getTitle());
    }

    @Test
    void testTabelaTemColunasCorretas() throws Exception {
        Field f = ContatoGui.class.getDeclaredField("tabela");
        f.setAccessible(true);
        JTable tabela = (JTable) f.get(gui);

        assertEquals("Nome", tabela.getColumnName(0));
        assertEquals("Telefone", tabela.getColumnName(1));
        assertEquals("Email", tabela.getColumnName(2));
    }

    @Test
    void testModeloTabelaTemTresColunas() throws Exception {
        Field f = ContatoGui.class.getDeclaredField("modeloTabela");
        f.setAccessible(true);
        DefaultTableModel modelo = (DefaultTableModel) f.get(gui);

        assertEquals(3, modelo.getColumnCount());
    }
    @Test
    void testTituloNaoEhNulo() {
        assertNotNull(gui.getTitle(), "O título da janela não deve ser nulo.");
    }

    @Test
    void testTituloDaJanela() {
        String tituloEsperado = "Agenda De Contatos";
        assertEquals(tituloEsperado, gui.getTitle(), "O título da janela deve ser 'Agenda De Contatos'.");
    }

    @Test
    void testTabelaTamanhoColunasCorretas() throws Exception {
        Field f = ContatoGui.class.getDeclaredField("tabela");
        f.setAccessible(true);
        JTable tabela = (JTable) f.get(gui);

        assertEquals(3, tabela.getColumnCount(), "A tabela deve ter 3 colunas.");
    }

    @Test
    void testColunasTabelaComNomesCorretos() throws Exception {
        Field f = ContatoGui.class.getDeclaredField("tabela");
        f.setAccessible(true);
        JTable tabela = (JTable) f.get(gui);

        String[] colunasEsperadas = {"Nome", "Telefone", "Email"};
        for (int i = 0; i < colunasEsperadas.length; i++) {
            assertEquals(colunasEsperadas[i], tabela.getColumnName(i));
        }
    }
    @Test
    void testLayoutPrincipalTemDoisComponentes() {
        Container contentPane = gui.getContentPane();
        Component[] componentes = contentPane.getComponents();
        assertEquals(2, componentes.length);
    }
    @Test
    void testOrdemCrescenteNomeAlterna() throws Exception {
        Field field = ContatoGui.class.getDeclaredField("ordemCrescenteNome");
        field.setAccessible(true);
        boolean valorAntes = field.getBoolean(gui);

        java.lang.reflect.Method metodo = ContatoGui.class.getDeclaredMethod("ordenarContatos", boolean.class);
        metodo.setAccessible(true);
        metodo.invoke(gui, true);

        boolean valorDepois = field.getBoolean(gui);
        assertNotEquals(valorAntes, valorDepois);
    }
}