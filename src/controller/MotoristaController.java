package controller;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class MotoristaController {
    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private Connection conn;

    @FXML private TableView<Motorista> tabela;
    @FXML private TableColumn<Motorista, Integer> colId;
    @FXML private TableColumn<Motorista, String> colNome, colCnh, colCategoria;

    @FXML private TextField txtId, txtNome, txtCnh, txtCategoria;

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
        colCnh.setCellValueFactory(c -> c.getValue().cnhProperty());
        colCategoria.setCellValueFactory(c -> c.getValue().categoriaCnhProperty());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(String.valueOf(newSel.getId()));
                txtNome.setText(newSel.getNome());
                txtCnh.setText(newSel.getCnh());
                txtCategoria.setText(newSel.getCategoriaCnh());
            }
        });
    }

    @FXML
    private void listar() {
        if (conn == null) return;
        ObservableList<Motorista> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM motorista";
        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Motorista(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cnh"),
                        rs.getString("categoria_cnh")
                ));
            }
            tabela.setItems(lista);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void cadastrar() {
        String sql = "INSERT INTO motorista (nome, cnh, categoria_cnh) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText());
            ps.setString(2, txtCnh.getText());
            ps.setString(3, txtCategoria.getText());
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void atualizar() {
        if (txtId.getText().isEmpty()) return;
        String sql = "UPDATE motorista SET nome=?, cnh=?, categoria_cnh=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText());
            ps.setString(2, txtCnh.getText());
            ps.setString(3, txtCategoria.getText());
            ps.setInt(4, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void deletar() {
        if (txtId.getText().isEmpty()) return;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM motorista WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void limparCampos() {
        txtId.clear(); txtNome.clear(); txtCnh.clear(); txtCategoria.clear();
        tabela.getSelectionModel().clearSelection();
    }

    // Classe de modelo interna
    public static class Motorista {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty nome, cnh, categoriaCnh;

        public Motorista(int id, String nome, String cnh, String categoriaCnh) {
            this.id = new SimpleIntegerProperty(id);
            this.nome = new SimpleStringProperty(nome);
            this.cnh = new SimpleStringProperty(cnh);
            this.categoriaCnh = new SimpleStringProperty(categoriaCnh);
        }
        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }
        public String getNome() { return nome.get(); }
        public SimpleStringProperty nomeProperty() { return nome; }
        public String getCnh() { return cnh.get(); }
        public SimpleStringProperty cnhProperty() { return cnh; }
        public String getCategoriaCnh() { return categoriaCnh.get(); }
        public SimpleStringProperty categoriaCnhProperty() { return categoriaCnh; }
    }
}