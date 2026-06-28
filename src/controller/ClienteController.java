package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class ClienteController {

    // Configurações do Banco de Dados
    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private final String user = "root";
    private final String password = "";
    private Connection conexao;

    // Componentes injetados do FXML
    @FXML private TableView<Cliente> tabela;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colCpf;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtEmail;

    // O método initialize roda AUTOMATICAMENTE assim que o FXML termina de carregar
    @FXML
    public void initialize() {
        conectarBanco();
        configurarTabela();
        listarClientes();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colCpf.setCellValueFactory(cellData -> cellData.getValue().cpfProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(String.valueOf(newSelection.getId()));
                txtNome.setText(newSelection.getNome());
                txtCpf.setText(newSelection.getCpf());
                txtEmail.setText(newSelection.getEmail());
            }
        });
    }

    private void conectarBanco() {
        try {
            this.conexao = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            alerta("Erro", "Erro ao conectar banco: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void listarClientes() {
        if (conexao == null) return;
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
        String select = "SELECT * FROM cliente";
        try (Statement stat = conexao.createStatement(); ResultSet rs = stat.executeQuery(select)) {
            while (rs.next()) {
                listaClientes.add(new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("email")));
            }
            tabela.setItems(listaClientes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cadastrarCliente() {
        String insert = "INSERT INTO cliente (nome, cpf, email) VALUES (?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(insert)) {
            ps.setString(1, txtNome.getText());
            ps.setString(2, txtCpf.getText());
            ps.setString(3, txtEmail.getText());
            ps.executeUpdate();
            alerta("Sucesso", "Cliente cadastrado!", Alert.AlertType.INFORMATION);
            limparCampos();
            listarClientes();
        } catch (SQLException e) {
            alerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void atualizarCliente() {
        if (txtId.getText().isEmpty()) return;
        String update = "UPDATE cliente SET nome = ?, cpf = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(update)) {
            ps.setString(1, txtNome.getText());
            ps.setString(2, txtCpf.getText());
            ps.setString(3, txtEmail.getText());
            ps.setInt(4, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            alerta("Sucesso", "Cliente atualizado!", Alert.AlertType.INFORMATION);
            limparCampos();
            listarClientes();
        } catch (SQLException e) {
            alerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deletarCliente() {
        if (txtId.getText().isEmpty()) return;
        String delete = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(delete)) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            alerta("Sucesso", "Cliente deletado!", Alert.AlertType.INFORMATION);
            limparCampos();
            listarClientes();
        } catch (SQLException e) {
            alerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtCpf.clear();
        txtEmail.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void alerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public static class Cliente {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty nome;
        private final SimpleStringProperty cpf;
        private final SimpleStringProperty email;

        public Cliente(int id, String nome, String cpf, String email) {
            this.id = new SimpleIntegerProperty(id);
            this.nome = new SimpleStringProperty(nome);
            this.cpf = new SimpleStringProperty(cpf);
            this.email = new SimpleStringProperty(email);
        }
        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }
        public String getNome() { return nome.get(); }
        public SimpleStringProperty nomeProperty() { return nome; }
        public String getCpf() { return cpf.get(); }
        public SimpleStringProperty cpfProperty() { return cpf; }
        public String getEmail() { return email.get(); }
        public SimpleStringProperty emailProperty() { return email; }
    }
}