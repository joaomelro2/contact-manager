package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ContatoGui extends JFrame {
    private AddEditPesquisar manipular;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private boolean ordemCrescenteNome = true;
    private boolean ordemCrescenteEmail = true;

    public ContatoGui() {
        manipular = new AddEditPesquisar();

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

        carregarContatosDoArquivo();
        atualizarTabela();

        setVisible(true);
    }

    private void adicionarContato(ActionEvent e) {
        String nome = JOptionPane.showInputDialog("Nome:");
        if (nome == null || nome.isEmpty()) return;

        String telefone;
        do {
            telefone = JOptionPane.showInputDialog("Telefone (deve começar com +351 e conter 9 dígitos):");
        } while (!validarTelefone(telefone));

        // Remover o código "+351" antes de armazenar
        String telefoneSemCodigo = telefone.replace("+351", "").trim();

        String email;
        do {
            email = JOptionPane.showInputDialog("Email (deve conter @):");
        } while (!validarEmail(email));

        manipular.adicionarContato(new Contato(nome, telefoneSemCodigo, email));  // Armazena sem +351
        salvarContatosNoArquivo();
        atualizarTabela();
    }

    private void editarContato(ActionEvent e) {
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

        // Remove o código "+351" antes de armazenar
        String novoTelefoneSemCodigo = novoTelefone.replace("+351", "").trim();

        String novoEmail;
        do {
            novoEmail = JOptionPane.showInputDialog("Novo Email (deve conter @):", modeloTabela.getValueAt(linhaSelecionada, 2));
        } while (!validarEmail(novoEmail));

        manipular.editarContato(nomeAntigo, novoNome, novoTelefoneSemCodigo, novoEmail);  // Armazena sem +351
        salvarContatosNoArquivo();
        atualizarTabela();
    }

    private void pesquisarContato(ActionEvent e) {
        String termo = JOptionPane.showInputDialog("Pesquisar por:");
        if (termo != null && !termo.isEmpty()) {
            List<Contato> resultados = manipular.pesquisarContato(termo);
            atualizarTabela(resultados);
        }
    }

    private void ordenarContatos(boolean porNome) {
        if (porNome) {
            manipular.ordenarPorNome(ordemCrescenteNome);
            ordemCrescenteNome = !ordemCrescenteNome;
        } else {
            manipular.ordenarPorEmail(ordemCrescenteEmail);
            ordemCrescenteEmail = !ordemCrescenteEmail;
        }
        atualizarTabela();
    }

    private void atualizarTabela() {
        atualizarTabela(manipular.getContatos());
    }

    private void atualizarTabela(List<Contato> lista) {
        modeloTabela.setRowCount(0);
        for (Contato contato : lista) {
            modeloTabela.addRow(new Object[]{contato.getNome(), contato.getTelefone(), contato.getEmail()});
        }
    }

    private void salvarContatosNoArquivo() {
        try {
            FicheiroContato.salvarContatos(manipular.getContatos());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar contatos no ficheiro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarContatosDoArquivo() {
        try {
            List<Contato> contatos = FicheiroContato.carregarContatos();
            for (Contato c : contatos) {
                manipular.adicionarContato(c);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar contatos do ficheiro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarTelefone(String telefone) {
        if (telefone != null && telefone.matches("\\+351\\d{9}")) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Telefone inválido! Deve começar com +351 e conter 9 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    private boolean validarEmail(String email) {
        if (email != null && email.contains("@")) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Email inválido! Deve conter @.", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContatoGui::new);
    }
}
