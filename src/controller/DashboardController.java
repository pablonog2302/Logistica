package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {

    @FXML private AnchorPane conteudoCentral;

    private void trocarTela(String fxml) {
        try {
            Parent novaTela = FXMLLoader.load(getClass().getResource("/" + fxml));
            conteudoCentral.getChildren().setAll(novaTela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Suas abas normais aqui...
    @FXML void abrirClientes() { trocarTela("view/clientes.fxml"); }
    @FXML void abrirPedidos() { trocarTela("view/pedido.fxml"); }
    @FXML void abrirItens() { trocarTela("view/itens_pedido.fxml"); }
    @FXML void abrirManifestos() { trocarTela("view/manifesto.fxml"); }
    @FXML void abrirMotoristas() { trocarTela("view/motorista.fxml"); }
    @FXML void abrirProdutos() { trocarTela("view/produto.fxml"); }
    @FXML void abrirStatus() { trocarTela("view/status.fxml"); }
    @FXML void abrirVeiculos() { trocarTela("view/veiculo.fxml"); }

    @FXML void sair() { System.exit(0); }
}