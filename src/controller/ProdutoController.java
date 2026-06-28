package controller;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class ProdutoController {
    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private Connection conn;

    @FXML private TableView<Produto> tabela;
    @FXML private TableColumn<Produto, Integer> colId, colEmpilhamento;
    @FXML private TableColumn<Produto, String> colNome, colObs;
    @FXML private TableColumn<Produto, Double> colLargura, colAltura;

    @FXML private TextField txtId, txtNome, txtLargura, txtAltura, txtEmpilhamento;
    @FXML private TextArea txtObs;

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
        colLargura.setCellValueFactory(c -> c.getValue().larguraProperty().asObject());
        colAltura.setCellValueFactory(c -> c.getValue().alturaProperty().asObject());
        colEmpilhamento.setCellValueFactory(c -> c.getValue().empilhamentoMaximoProperty().asObject());
        colObs.setCellValueFactory(c -> c.getValue().obsProperty());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(String.valueOf(newSel.getId()));
                txtNome.setText(newSel.getNome());
                txtLargura.setText(String.valueOf(newSel.getLargura()));
                txtAltura.setText(String.valueOf(newSel.getAltura()));
                txtEmpilhamento.setText(String.valueOf(newSel.getEmpilhamentoMaximo()));
                txtObs.setText(newSel.getObs());
            }
        });
    }

    @FXML
    private void listar() {
        if (conn == null) return;
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM produto";
        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getInt("id"), rs.getString("nome"),
                        rs.getDouble("largura"), rs.getDouble("altura"),
                        rs.getInt("empilhamento_maximo"), rs.getString("obs")
                ));
            }
            tabela.setItems(lista);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void cadastrar() {
        String sql = "INSERT INTO produto (nome, largura, altura, empilhamento_maximo, obs) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText());
            ps.setDouble(2, Double.parseDouble(txtLargura.getText()));
            ps.setDouble(3, Double.parseDouble(txtAltura.getText()));
            ps.setInt(4, Integer.parseInt(txtEmpilhamento.getText()));
            ps.setString(5, txtObs.getText());
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void atualizar() {
        if (txtId.getText().isEmpty()) return;
        String sql = "UPDATE produto SET nome=?, largura=?, altura=?, empilhamento_maximo=?, obs=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText());
            ps.setDouble(2, Double.parseDouble(txtLargura.getText()));
            ps.setDouble(3, Double.parseDouble(txtAltura.getText()));
            ps.setInt(4, Integer.parseInt(txtEmpilhamento.getText()));
            ps.setString(5, txtObs.getText());
            ps.setInt(6, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void deletar() {
        if (txtId.getText().isEmpty()) return;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM produto WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void limparCampos() {
        txtId.clear(); txtNome.clear(); txtLargura.clear(); txtAltura.clear();
        txtEmpilhamento.clear(); txtObs.clear();
        tabela.getSelectionModel().clearSelection();
    }

    // Classe de modelo interna
    public static class Produto {
        private final SimpleIntegerProperty id, empilhamentoMaximo;
        private final SimpleStringProperty nome, obs;
        private final SimpleDoubleProperty largura, altura;

        public Produto(int id, String nome, double largura, double altura, int empilhamento, String obs) {
            this.id = new SimpleIntegerProperty(id);
            this.nome = new SimpleStringProperty(nome);
            this.largura = new SimpleDoubleProperty(largura);
            this.altura = new SimpleDoubleProperty(altura);
            this.empilhamentoMaximo = new SimpleIntegerProperty(empilhamento);
            this.obs = new SimpleStringProperty(obs);
        }
        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }
        public String getNome() { return nome.get(); }
        public SimpleStringProperty nomeProperty() { return nome; }
        public double getLargura() { return largura.get(); }
        public SimpleDoubleProperty larguraProperty() { return largura; }
        public double getAltura() { return altura.get(); }
        public SimpleDoubleProperty alturaProperty() { return altura; }
        public int getEmpilhamentoMaximo() { return empilhamentoMaximo.get(); }
        public SimpleIntegerProperty empilhamentoMaximoProperty() { return empilhamentoMaximo; }
        public String getObs() { return obs != null ? obs.get() : ""; }
        public SimpleStringProperty obsProperty() { return obs; }
    }
}