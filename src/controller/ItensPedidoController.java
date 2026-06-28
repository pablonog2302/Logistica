package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class ItensPedidoController {

    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private final String user = "root";
    private final String password = "";
    private Connection conexao;

    @FXML private TableView<ItemPedido> tabela;
    @FXML private TableColumn<ItemPedido, Integer> colId;
    @FXML private TableColumn<ItemPedido, Integer> colPedidoId;
    @FXML private TableColumn<ItemPedido, Integer> colProdutoId;
    @FXML private TableColumn<ItemPedido, Integer> colQuantidade;
    @FXML private TableColumn<ItemPedido, Double> colPesoTotal;

    @FXML private TextField txtId;
    @FXML private TextField txtPedidoId;
    @FXML private TextField txtProdutoId;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPesoTotal;

    @FXML
    public void initialize() {
        conectarBanco();
        configurarTabela();
        listarItens();
    }

    private void conectarBanco() {
        try {
            this.conexao = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            alerta("Erro", "Erro ao conectar banco: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void configurarTabela() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colPedidoId.setCellValueFactory(cellData -> cellData.getValue().pedidoIdProperty().asObject());
        colProdutoId.setCellValueFactory(cellData -> cellData.getValue().produtoIdProperty().asObject());
        colQuantidade.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty().asObject());
        colPesoTotal.setCellValueFactory(cellData -> cellData.getValue().pesoTotalProperty().asObject());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(String.valueOf(newSelection.getId()));
                txtPedidoId.setText(String.valueOf(newSelection.getPedidoId()));
                txtProdutoId.setText(String.valueOf(newSelection.getProdutoId()));
                txtQuantidade.setText(String.valueOf(newSelection.getQuantidade()));
                txtPesoTotal.setText(String.valueOf(newSelection.getPesoTotal()));
            }
        });
    }

    @FXML
    private void listarItens() {
        if (conexao == null) return;
        ObservableList<ItemPedido> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM itens_pedido";
        try (Statement stat = conexao.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ItemPedido(
                        rs.getInt("id"),
                        rs.getInt("quantidade"),
                        rs.getDouble("peso_total_item"),
                        rs.getInt("pedido_id"),
                        rs.getInt("produto_id")
                ));
            }
            tabela.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cadastrarItem() {
        String sql = "INSERT INTO itens_pedido (quantidade, peso_total_item, pedido_id, produto_id) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txtQuantidade.getText()));
            ps.setDouble(2, Double.parseDouble(txtPesoTotal.getText()));
            ps.setInt(3, Integer.parseInt(txtPedidoId.getText()));
            ps.setInt(4, Integer.parseInt(txtProdutoId.getText()));
            ps.executeUpdate();
            alerta("Sucesso", "Item cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();
            listarItens();
        } catch (Exception e) {
            alerta("Erro", "Falha ao salvar. Verifique os dados: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void atualizarItem() {
        if (txtId.getText().isEmpty()) return;
        String sql = "UPDATE itens_pedido SET quantidade = ?, peso_total_item = ?, pedido_id = ?, produto_id = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txtQuantidade.getText()));
            ps.setDouble(2, Double.parseDouble(txtPesoTotal.getText()));
            ps.setInt(3, Integer.parseInt(txtPedidoId.getText()));
            ps.setInt(4, Integer.parseInt(txtProdutoId.getText()));
            ps.setInt(5, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            alerta("Sucesso", "Item atualizado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();
            listarItens();
        } catch (Exception e) {
            alerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deletarItem() {
        if (txtId.getText().isEmpty()) return;
        String sql = "DELETE FROM itens_pedido WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            alerta("Sucesso", "Item removido!", Alert.AlertType.INFORMATION);
            limparCampos();
            listarItens();
        } catch (SQLException e) {
            alerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limparCampos() {
        txtId.clear();
        txtPedidoId.clear();
        txtProdutoId.clear();
        txtQuantidade.clear();
        txtPesoTotal.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void alerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    // Classe de Modelo interna ajustada para os seus campos
    public static class ItemPedido {
        private final SimpleIntegerProperty id;
        private final SimpleIntegerProperty quantidade;
        private final SimpleDoubleProperty pesoTotal;
        private final SimpleIntegerProperty pedidoId;
        private final SimpleIntegerProperty produtoId;

        public ItemPedido(int id, int quantidade, double pesoTotal, int pedidoId, int produtoId) {
            this.id = new SimpleIntegerProperty(id);
            this.quantidade = new SimpleIntegerProperty(quantidade);
            this.pesoTotal = new SimpleDoubleProperty(pesoTotal);
            this.pedidoId = new SimpleIntegerProperty(pedidoId);
            this.produtoId = new SimpleIntegerProperty(produtoId);
        }

        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }

        public int getQuantidade() { return quantidade.get(); }
        public SimpleIntegerProperty quantidadeProperty() { return quantidade; }

        public double getResultPesoTotal() { return pesoTotal.get(); } // Evita conflito de nome
        public double getPesoTotal() { return pesoTotal.get(); }
        public SimpleDoubleProperty pesoTotalProperty() { return pesoTotal; }

        public int getPedidoId() { return pedidoId.get(); }
        public SimpleIntegerProperty pedidoIdProperty() { return pedidoId; }

        public int getProdutoId() { return produtoId.get(); }
        public SimpleIntegerProperty produtoIdProperty() { return produtoId; }
    }
}