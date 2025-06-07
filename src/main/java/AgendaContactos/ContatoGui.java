package AgendaContactos;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 * Classe que representa a interface gráfica para gestão de contactos.
 * Permite adicionar, editar, pesquisar e ordenar os contactos numa tabela.
 * Utiliza um modelo de dados fornecido pela classe {@link GestorContatos}.
 */
public class ContatoGui extends JFrame {
    /** Gestor de contactos que manipula os dados. */
    private GestorContatos manipular;
     /** Gestor de contactos que manipula os dados. */
    private JTable tabela;
    /** Modelo da tabela de contactos. */
    private DefaultTableModel modeloTabela;
    /** Controle da ordem crescente/decrescente ao ordenar por nome. */
    private boolean ordemCrescenteNome = true;
    /** Controle da ordem crescente/decrescente ao ordenar por e-mail. */
    private boolean ordemCrescenteEmail = true;

     /**
     * Construtor que inicializa a interface gráfica de contactos.
     * Carrega os dados a partir do repositório e configura os componentes visuais.
     */
    public ContatoGui() {
        try {
          manipular = new GestorContatos(RepositorioFactory.criarRepositorio());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar contatos!", "Erro", JOptionPane.ERROR_MESSAGE);
            manipular = null;
        }

        setTitle("Agenda De Contatos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Telefone", "Email"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnOrdenarPorNome = new JButton("Ordenar por Nome");
        JButton btnOrdenarPorEmail = new JButton("Ordenar por Email");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnOrdenarPorNome);
        painelBotoes.add(btnOrdenarPorEmail);

        btnAdicionar.addActionListener(this::adicionarContato);
        btnEditar.addActionListener(this::editarContato);
        btnPesquisar.addActionListener(this::pesquisarContato);
        btnOrdenarPorNome.addActionListener(e -> ordenarContatos(true));
        btnOrdenarPorEmail.addActionListener(e -> ordenarContatos(false));

        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabela();

        setVisible(true);
    }

    /**
     * Método responsável por adicionar um novo contacto.
     * Solicita ao utilizador os dados e valida antes de adicionar.
     * 
     * @param e Evento acionado ao clicar no botão "Adicionar".
     */
    private void adicionarContato(ActionEvent e) {
        if (manipular == null) return;

        String nome = JOptionPane.showInputDialog("Nome:");
        if (nome == null || nome.isEmpty()) return;

        String telefone;
        do {
            telefone = JOptionPane.showInputDialog("Telefone (deve começar com +351 e conter 9 dígitos):");
        } while (!validarTelefone(telefone));

        String email;
        do {
            email = JOptionPane.showInputDialog("Email (deve conter @):");
        } while (!validarEmail(email));

        try {
            Contato novo = new ContatoBuilder()
                .comNome(nome)
                .comTelefone(telefone)
                .comEmail(email)
                .construir();

            manipular.adicionarContato(novo);
            atualizarTabela();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar contato!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

     /**
     * Método responsável por editar um contacto existente.
     * O utilizador seleciona um contacto para editar e insere os novos dados.
     * 
     * @param e Evento acionado ao clicar no botão "Editar".
     */
    private void editarContato(ActionEvent e) {
        if (manipular == null) return;

        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um contato para editar.");
            return;
        }

        String nomeAntigo = modeloTabela.getValueAt(linhaSelecionada, 0).toString();
        String novoNome = JOptionPane.showInputDialog("Novo Nome:", nomeAntigo);
        if (novoNome == null || novoNome.isEmpty()) return;

        String novoTelefone;
        do {
            novoTelefone = JOptionPane.showInputDialog("Novo Telefone (deve começar com +351 e conter 9 dígitos):", modeloTabela.getValueAt(linhaSelecionada, 1));
        } while (!validarTelefone(novoTelefone));

        String novoEmail;
        do {
            novoEmail = JOptionPane.showInputDialog("Novo Email (deve conter @):", modeloTabela.getValueAt(linhaSelecionada, 2));
        } while (!validarEmail(novoEmail));

        try {
            manipular.editarContato(nomeAntigo, novoNome, novoTelefone, novoEmail);
            atualizarTabela();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar contato!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para pesquisar contactos com base num termo de pesquisa.
     * 
     * @param e Evento acionado ao clicar no botão "Pesquisar".
     */
    private void pesquisarContato(ActionEvent e) {
        if (manipular == null) return;

        String termo = JOptionPane.showInputDialog("Pesquisar por:");
        if (termo != null && !termo.isEmpty()) {
            List<Contato> resultados = manipular.pesquisarContato(termo);
            atualizarTabela(resultados);
        }
    }

     /**
     * Método para ordenar os contactos por nome ou e-mail.
     * Alterna a ordem crescente/decrescente.
     * 
     * @param porNome Se verdadeiro, ordena por nome; caso contrário, por e-mail.
     */
    private void ordenarContatos(boolean porNome) {
        if (manipular == null) return;

        if (porNome) {
            manipular.ordenarPorNome(ordemCrescenteNome);
            ordemCrescenteNome = !ordemCrescenteNome;
        } else {
            manipular.ordenarPorEmail(ordemCrescenteEmail);
            ordemCrescenteEmail = !ordemCrescenteEmail;
        }
        atualizarTabela();
    }

    /**
     * Atualiza a tabela com todos os contactos.
     */
    private void atualizarTabela() {
        if (manipular != null) {
            atualizarTabela(manipular.getTodosContatos());
        }
    }

     /**
     * Atualiza a tabela com uma lista específica de contactos.
     * 
     * @param lista Lista de contactos a ser exibida na tabela.
     */
    private void atualizarTabela(List<Contato> lista) {
        modeloTabela.setRowCount(0);
        for (Contato contato : lista) {
            modeloTabela.addRow(new Object[]{contato.getNome(), contato.getTelefone(), contato.getEmail()});
        }
    }

    /**
     * Valida se o telefone segue o formato correto (deve começar com +351 e ter 9 dígitos).
     * 
     * @param telefone O número de telefone a validar.
     * @return Verdadeiro se o telefone for válido, falso caso contrário.
     */
    private boolean validarTelefone(String telefone) {
        if (telefone != null && telefone.matches("\\+351\\d{9}")) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Telefone inválido! Deve começar com +351 e conter 9 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    /**
     * Valida se o e-mail contém o símbolo "@".
     * 
     * @param email O e-mail a validar.
     * @return Verdadeiro se o e-mail for válido, falso caso contrário.
     */
    private boolean validarEmail(String email) {
        if (email != null && email.contains("@")) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Email inválido! Deve conter @.", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
