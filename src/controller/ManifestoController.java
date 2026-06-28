package controller;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class ManifestoController {
    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private Connection conn;

    @FXML private TableView<Manifesto> tabela;
    @FXML private TableColumn<Manifesto, Integer> colId, colVolume, colVeiculo, colMotorista, colPedido;
    @FXML private TableColumn<Manifesto, String> colData;
    @FXML private TableColumn<Manifesto, Double> colPeso;

    @FXML private TextField txtId, txtDataPartida, txtVolume, txtPeso, txtVeiculoId, txtMotoristaId, txtPedidoId;

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
        colData.setCellValueFactory(c -> c.getValue().dataPartidaProperty());
        colVolume.setCellValueFactory(c -> c.getValue().volumeProperty().asObject());
        colPeso.setCellValueFactory(c -> c.getValue().pesoProperty().asObject());
        colVeiculo.setCellValueFactory(c -> c.getValue().veiculoIdProperty().asObject());
        colMotorista.setCellValueFactory(c -> c.getValue().motoristaIdProperty().asObject());
        colPedido.setCellValueFactory(c -> c.getValue().pedidoIdProperty().asObject());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(String.valueOf(newSel.getId()));
                txtDataPartida.setText(newSel.getDataPartida());
                txtVolume.setText(String.valueOf(newSel.getVolume()));
                txtPeso.setText(String.valueOf(newSel.getPeso()));
                txtVeiculoId.setText(String.valueOf(newSel.getVeiculoId()));
                txtMotoristaId.setText(String.valueOf(newSel.getMotoristaId()));
                txtPedidoId.setText(String.valueOf(newSel.getPedidoId()));
            }
        });
    }

    @FXML
    private void listar() {
        if (conn == null) return;
        ObservableList<Manifesto> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM manifestos_carga";
        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Manifesto(
                        rs.getInt("id"),
                        rs.getString("data_partida"),
                        rs.getInt("volume_total_ocupado"),
                        rs.getDouble("peso_total_carregado_kg"),
                        rs.getInt("veiculo_id"),
                        rs.getInt("motorista_id"),
                        rs.getInt("pedido_id")
                ));
            }
            tabela.setItems(lista);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void cadastrar() {
        String sql = "INSERT INTO manifestos_carga (data_partida, volume_total_ocupado, peso_total_carregado_kg, veiculo_id, motorista_id, pedido_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtDataPartida.getText());
            ps.setInt(2, Integer.parseInt(txtVolume.getText()));
            ps.setDouble(3, Double.parseDouble(txtPeso.getText()));
            ps.setInt(4, Integer.parseInt(txtVeiculoId.getText()));
            ps.setInt(5, Integer.parseInt(txtMotoristaId.getText()));
            ps.setInt(6, Integer.parseInt(txtPedidoId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void atualizar() {
        if (txtId.getText().isEmpty()) return;
        String sql = "UPDATE manifestos_carga SET data_partida=?, volume_total_ocupado=?, peso_total_carregado_kg=?, veiculo_id=?, motorista_id=?, pedido_id=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtDataPartida.getText());
            ps.setInt(2, Integer.parseInt(txtVolume.getText()));
            ps.setDouble(3, Double.parseDouble(txtPeso.getText()));
            ps.setInt(4, Integer.parseInt(txtVeiculoId.getText()));
            ps.setInt(5, Integer.parseInt(txtMotoristaId.getText()));
            ps.setInt(6, Integer.parseInt(txtPedidoId.getText()));
            ps.setInt(7, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void deletar() {
        if (txtId.getText().isEmpty()) return;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM manifestos_carga WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            limparCampos();
            listar();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void limparCampos() {
        txtId.clear(); txtDataPartida.clear(); txtVolume.clear(); txtPeso.clear();
        txtVeiculoId.clear(); txtMotoristaId.clear(); txtPedidoId.clear();
        tabela.getSelectionModel().clearSelection();
    }

    public static class Manifesto {
        private final SimpleIntegerProperty id, volume, veiculoId, motoristaId, pedidoId;
        private final SimpleStringProperty dataPartida;
        private final SimpleDoubleProperty peso;

        public Manifesto(int id, String data, int vol, double peso, int veic, int mot, int ped) {
            this.id = new SimpleIntegerProperty(id);
            this.dataPartida = new SimpleStringProperty(data);
            this.volume = new SimpleIntegerProperty(vol);
            this.peso = new SimpleDoubleProperty(peso);
            this.veiculoId = new SimpleIntegerProperty(veic);
            this.motoristaId = new SimpleIntegerProperty(mot);
            this.pedidoId = new SimpleIntegerProperty(ped);
        }
        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }
        public String getDataPartida() { return dataPartida.get(); }
        public SimpleStringProperty dataPartidaProperty() { return dataPartida; }
        public int getVolume() { return volume.get(); }
        public SimpleIntegerProperty volumeProperty() { return volume; }
        public double getPeso() { return peso.get(); }
        public SimpleDoubleProperty pesoProperty() { return peso; }
        public int getVeiculoId() { return veiculoId.get(); }
        public SimpleIntegerProperty veiculoIdProperty() { return veiculoId; }
        public int getMotoristaId() { return motoristaId.get(); }
        public SimpleIntegerProperty motoristaIdProperty() { return motoristaId; }
        public int getPedidoId() { return pedidoId.get(); }
        public SimpleIntegerProperty pedidoIdProperty() { return pedidoId; }
    }
}