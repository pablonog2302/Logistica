package controller;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class StatusController {
    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private Connection conn;

    @FXML private TableView<StatusModel> tabela;
    @FXML private TableColumn<StatusModel, Integer> colId;
    @FXML private TableColumn<StatusModel, String> colNome;

    @FXML private TextField txtId, txtNome;

    @FXML
    public void initialize() {
        conectar();
        configurarColunas();
        listar();
    }

    private void conectar() {
        try { conn = DriverManager.getConnection(url, "root", ""); } catch (Exception e) { e.printStackTrace(); }
    }

    private void configurarColunas() {
        colId.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());

        // Joga a linha selecionada de volta nos campos
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(String.valueOf(newSel.getId()));
                txtNome.setText(newSel.getNome());
            }
        });
    }

    @FXML
    private void listar() {
        if (conn == null) return;
        ObservableList<StatusModel> lista = FXCollections.observableArrayList();
        String sql = "SELECT id, nome FROM status"; // Apenas id e nome
        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new StatusModel(rs.getInt("id"), rs.getString("nome")));
            }
            tabela.setItems(lista);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void cadastrar() {
        String sql = "INSERT INTO status (nome) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText());
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void atualizar() {
        if (txtId.getText().isEmpty()) return;
        String sql = "UPDATE status SET nome=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText());
            ps.setInt(2, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void deletar() {
        if (txtId.getText().isEmpty()) return;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM status WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void limparCampos() {
        txtId.clear(); txtNome.clear();
        tabela.getSelectionModel().clearSelection();
    }

    // Modelo interno simplificado apenas com id e nome
    public static class StatusModel {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty nome;

        public StatusModel(int id, String nome) {
            this.id = new SimpleIntegerProperty(id);
            this.nome = new SimpleStringProperty(nome);
        }
        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }
        public String getNome() { return nome.get(); }
        public SimpleStringProperty nomeProperty() { return nome; }
    }
}