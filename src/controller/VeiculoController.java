package controller;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class VeiculoController {
    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private Connection conn;

    @FXML private TableView<Veiculo> tabela;
    @FXML private TableColumn<Veiculo, Integer> colId;
    @FXML private TableColumn<Veiculo, String> colPlaca, colModelo;
    @FXML private TableColumn<Veiculo, Double> colPeso, colLargura, colAltura, colComprimento, colVolume;

    @FXML private TextField txtId, txtPlaca, txtModelo, txtCapacidadePeso, txtLargura, txtAltura, txtComprimento, txtVolumeMax;

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
        colPlaca.setCellValueFactory(c -> c.getValue().placaProperty());
        colModelo.setCellValueFactory(c -> c.getValue().modeloProperty());
        colPeso.setCellValueFactory(c -> c.getValue().capacidadePesoProperty().asObject());
        colLargura.setCellValueFactory(c -> c.getValue().larguraInternaProperty().asObject());
        colAltura.setCellValueFactory(c -> c.getValue().alturaInternaProperty().asObject());
        colComprimento.setCellValueFactory(c -> c.getValue().comprimentoInternoProperty().asObject());
        colVolume.setCellValueFactory(c -> c.getValue().volumeMaxProperty().asObject());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(String.valueOf(newSel.getId()));
                txtPlaca.setText(newSel.getPlaca());
                txtModelo.setText(newSel.getModelo());
                txtCapacidadePeso.setText(String.valueOf(newSel.getCapacidadePeso()));
                txtLargura.setText(String.valueOf(newSel.getLarguraInterna()));
                txtAltura.setText(String.valueOf(newSel.getAlturaInterna()));
                txtComprimento.setText(String.valueOf(newSel.getComprimentoInterno()));
                txtVolumeMax.setText(String.valueOf(newSel.getVolumeMax()));
            }
        });
    }

    @FXML
    private void listar() {
        if (conn == null) return;
        ObservableList<Veiculo> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM veiculo";
        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Veiculo(
                        rs.getInt("id"), rs.getString("placa"), rs.getString("modelo"),
                        rs.getDouble("capacidade_peso_kg"), rs.getDouble("largura_interna"),
                        rs.getDouble("altura_interna"), rs.getDouble("comprimento_interno"),
                        rs.getDouble("volume_max_m3")
                ));
            }
            tabela.setItems(lista);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void cadastrar() {
        String sql = "INSERT INTO veiculo (placa, modelo, capacidade_peso_kg, largura_interna, altura_interna, comprimento_interno, volume_max_m3) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtPlaca.getText());
            ps.setString(2, txtModelo.getText());
            ps.setDouble(3, Double.parseDouble(txtCapacidadePeso.getText()));
            ps.setDouble(4, Double.parseDouble(txtLargura.getText()));
            ps.setDouble(5, Double.parseDouble(txtAltura.getText()));
            ps.setDouble(6, Double.parseDouble(txtComprimento.getText()));
            ps.setDouble(7, Double.parseDouble(txtVolumeMax.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void atualizar() {
        if (txtId.getText().isEmpty()) return;
        String sql = "UPDATE veiculo SET placa=?, modelo=?, capacidade_peso_kg=?, largura_interna=?, altura_interna=?, comprimento_interno=?, volume_max_m3=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtPlaca.getText());
            ps.setString(2, txtModelo.getText());
            ps.setDouble(3, Double.parseDouble(txtCapacidadePeso.getText()));
            ps.setDouble(4, Double.parseDouble(txtLargura.getText()));
            ps.setDouble(5, Double.parseDouble(txtAltura.getText()));
            ps.setDouble(6, Double.parseDouble(txtComprimento.getText()));
            ps.setDouble(7, Double.parseDouble(txtVolumeMax.getText()));
            ps.setInt(8, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void deletar() {
        if (txtId.getText().isEmpty()) return;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM veiculo WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void limparCampos() {
        txtId.clear(); txtPlaca.clear(); txtModelo.clear(); txtCapacidadePeso.clear();
        txtLargura.clear(); txtAltura.clear(); txtComprimento.clear(); txtVolumeMax.clear();
        tabela.getSelectionModel().clearSelection();
    }

    public static class Veiculo {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty placa, modelo;
        private final SimpleDoubleProperty capacidadePeso, larguraInterna, alturaInterna, comprimentoInterno, volumeMax;

        public Veiculo(int id, String placa, String modelo, double peso, double larg, double alt, double comp, double vol) {
            this.id = new SimpleIntegerProperty(id);
            this.placa = new SimpleStringProperty(placa);
            this.modelo = new SimpleStringProperty(modelo);
            this.capacidadePeso = new SimpleDoubleProperty(peso);
            this.larguraInterna = new SimpleDoubleProperty(larg);
            this.alturaInterna = new SimpleDoubleProperty(alt);
            this.comprimentoInterno = new SimpleDoubleProperty(comp);
            this.volumeMax = new SimpleDoubleProperty(vol);
        }

        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }
        public String getPlaca() { return placa.get(); }
        public SimpleStringProperty placaProperty() { return placa; }
        public String getModelo() { return modelo.get(); }
        public SimpleStringProperty modeloProperty() { return modelo; }
        public double getCapacidadePeso() { return capacidadePeso.get(); }
        public SimpleDoubleProperty capacidadepesoProperty() { return capacidadePeso; }
        public SimpleDoubleProperty capacidadePesoProperty() { return capacidadePeso; }
        public double getLarguraInterna() { return larguraInterna.get(); }
        public SimpleDoubleProperty larguraInternaProperty() { return larguraInterna; }
        public double getAlturaInterna() { return alturaInterna.get(); }
        public SimpleDoubleProperty alturaInternaProperty() { return alturaInterna; }
        public double getComprimentoInterno() { return comprimentoInterno.get(); }
        public SimpleDoubleProperty comprimentoInternoProperty() { return comprimentoInterno; }
        public double getVolumeMax() { return volumeMax.get(); }
        public SimpleDoubleProperty volumeMaxProperty() { return volumeMax; }
    }
}