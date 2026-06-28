package controller;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.*;

public class PedidoController {
    private final String url = "jdbc:mysql://localhost:3306/logistica";
    private final String user = "root";
    private final String password = "";
    private Connection conn;

    @FXML private TableView<Pedido> tabela;
    @FXML private TableColumn<Pedido, Integer> colId;
    @FXML private TableColumn<Pedido, String> colDataEmissao;
    @FXML private TableColumn<Pedido, Integer> colClienteRemetente;
    @FXML private TableColumn<Pedido, Integer> colClienteDestinatario;
    @FXML private TableColumn<Pedido, String> colStatusId; // Alterado para String para exibir o NOME do status na tabela

    @FXML private TextField txtId;
    @FXML private TextField txtDataEmissao;
    @FXML private TextField txtClienteRemetenteId;
    @FXML private TextField txtClienteDestinatarioId;

    // INTEGRADO: Trocamos o TextField por um ComboBox que carrega os modelos do Status
    @FXML private ComboBox<StatusController.StatusModel> cbStatus;

    @FXML
    public void initialize() {
        conectar();
        configurarComboBoxStatus(); // Inicializa o ComboBox com os dados do banco
        configurarColunas();
        listar();
    }

    private void conectar() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao conectar banco: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // INTEGRADO: Método para buscar os status do banco e preencher o ComboBox
    private void configurarComboBoxStatus() {
        ObservableList<StatusController.StatusModel> listaStatus = FXCollections.observableArrayList();
        String sql = "SELECT id, nome FROM status";
        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                listaStatus.add(new StatusController.StatusModel(rs.getInt("id"), rs.getString("nome")));
            }
            cbStatus.setItems(listaStatus);

            // Define como o objeto StatusModel será exibido textualmente no ComboBox
            cbStatus.setConverter(new StringConverter<StatusController.StatusModel>() {
                @Override
                public String toString(StatusController.StatusModel status) {
                    return status != null ? status.getNome() : "";
                }
                @Override
                public StatusController.StatusModel fromString(String string) {
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarColunas() {
        colId.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        colDataEmissao.setCellValueFactory(c -> c.getValue().dataEmissaoProperty());
        colClienteRemetente.setCellValueFactory(c -> c.getValue().clienteRemetenteIdProperty().asObject());
        colClienteDestinatario.setCellValueFactory(c -> c.getValue().clienteDestinatarioIdProperty().asObject());
        colStatusId.setCellValueFactory(c -> c.getValue().statusNomeProperty()); // Exibe o nome textual na tabela

        // Clique na tabela joga os dados de volta para os campos de texto e ComboBox
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(String.valueOf(newSelection.getId()));
                txtDataEmissao.setText(newSelection.getDataEmissao());
                txtClienteRemetenteId.setText(String.valueOf(newSelection.getClienteRemetenteId()));
                txtClienteDestinatarioId.setText(String.valueOf(newSelection.getClienteDestinatarioId()));

                // Seleciona automaticamente o status correto no ComboBox baseado no ID do pedido clicado
                for (StatusController.StatusModel status : cbStatus.getItems()) {
                    if (status.getId() == newSelection.getStatusId()) {
                        cbStatus.setValue(status);
                        break;
                    }
                }
            }
        });
    }

    @FXML
    private void listar() {
        if (conn == null) return;
        ObservableList<Pedido> lista = FXCollections.observableArrayList();

        // INTEGRADO: INNER JOIN para trazer tanto o ID quanto o NOME textual do Status correspondente
        String sql = "SELECT p.id, p.data_emissao, p.cliente_remetente_id, p.cliente_destinatario_id, p.status_id, s.nome AS status_nome " +
                "FROM pedido p " +
                "INNER JOIN status s ON p.status_id = s.id";

        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Pedido(
                        rs.getInt("id"),
                        rs.getString("data_emissao"),
                        rs.getInt("cliente_remetente_id"),
                        rs.getInt("cliente_destinatario_id"),
                        rs.getInt("status_id"),
                        rs.getString("status_nome") // Adicionado parâmetro do nome
                ));
            }
            tabela.setItems(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salvarPedido() {
        StatusController.StatusModel statusSelecionado = cbStatus.getValue();
        if (statusSelecionado == null) {
            exibirAlerta("Aviso", "Selecione um status válido!", Alert.AlertType.WARNING);
            return;
        }

        String sql = "INSERT INTO pedido (data_emissao, cliente_remetente_id, cliente_destinatario_id, status_id) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtDataEmissao.getText());
            ps.setInt(2, Integer.parseInt(txtClienteRemetenteId.getText()));
            ps.setInt(3, Integer.parseInt(txtClienteDestinatarioId.getText()));
            ps.setInt(4, statusSelecionado.getId()); // Pega o ID direto do item selecionado no ComboBox
            ps.executeUpdate();
            exibirAlerta("Sucesso", "Pedido cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();
            listar();
        } catch (Exception e) {
            exibirAlerta("Erro", "Falha ao salvar. Verifique os dados: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void atualizarPedido() {
        if (txtId.getText().isEmpty()) return;
        StatusController.StatusModel statusSelecionado = cbStatus.getValue();
        if (statusSelecionado == null) return;

        String sql = "UPDATE pedido SET data_emissao = ?, cliente_remetente_id = ?, cliente_destinatario_id = ?, status_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtDataEmissao.getText());
            ps.setInt(2, Integer.parseInt(txtClienteRemetenteId.getText()));
            ps.setInt(3, Integer.parseInt(txtClienteDestinatarioId.getText()));
            ps.setInt(4, statusSelecionado.getId()); // Atualiza usando o ID do ComboBox
            ps.setInt(5, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            exibirAlerta("Sucesso", "Pedido atualizado!", Alert.AlertType.INFORMATION);
            limparCampos();
            listar();
        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void excluirPedido() {
        if (txtId.getText().isEmpty()) return;
        String sql = "DELETE FROM pedido WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            exibirAlerta("Sucesso", "Pedido removido!", Alert.AlertType.INFORMATION);
            limparCampos();
            listar();
        } catch (SQLException e) {
            exibirAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limparCampos() {
        txtId.clear();
        txtDataEmissao.clear();
        txtClienteRemetenteId.clear();
        txtClienteDestinatarioId.clear();
        cbStatus.setValue(null); // Limpa a seleção do ComboBox
        tabela.getSelectionModel().clearSelection();
    }

    private void exibirAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Classe de modelo interna atualizada
    public static class Pedido {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty dataEmissao;
        private final SimpleIntegerProperty clienteRemetenteId;
        private final SimpleIntegerProperty clienteDestinatarioId;
        private final SimpleIntegerProperty statusId;
        private final SimpleStringProperty statusNome; // Nova propriedade para o nome legível

        public Pedido(int id, String dataEmissao, int clienteRemetenteId, int clienteDestinatarioId, int statusId, String statusNome) {
            this.id = new SimpleIntegerProperty(id);
            this.dataEmissao = new SimpleStringProperty(dataEmissao);
            this.clienteRemetenteId = new SimpleIntegerProperty(clienteRemetenteId);
            this.clienteDestinatarioId = new SimpleIntegerProperty(clienteDestinatarioId);
            this.statusId = new SimpleIntegerProperty(statusId);
            this.statusNome = new SimpleStringProperty(statusNome);
        }

        public int getId() { return id.get(); }
        public SimpleIntegerProperty idProperty() { return id; }

        public String getDataEmissao() { return dataEmissao.get(); }
        public SimpleStringProperty dataEmissaoProperty() { return dataEmissao; }

        public int getClienteRemetenteId() { return clienteRemetenteId.get(); }
        public SimpleIntegerProperty clienteRemetenteIdProperty() { return clienteRemetenteId; }

        public int getClienteDestinatarioId() { return clienteDestinatarioId.get(); }
        public SimpleIntegerProperty clienteDestinatarioIdProperty() { return clienteDestinatarioId; }

        public int getStatusId() { return statusId.get(); }
        public SimpleIntegerProperty statusIdProperty() { return statusId; }

        public String getStatusNome() { return statusNome.get(); }
        public SimpleStringProperty statusNomeProperty() { return statusNome; }
    }
}