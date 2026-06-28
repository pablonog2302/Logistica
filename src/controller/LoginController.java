package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;

    @FXML
    void efetuarLogin() { // Este método é chamado ao clicar no botão "Entrar"
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        // Validação simples de teste
        if ("admin".equals(usuario) && "123".equals(senha)) {
            abrirDashboard();
        } else {
            System.err.println("Usuário ou senha inválidos!");
        }
    }

    private void abrirDashboard() {
        try {
            // 1. Carrega o fxml do seu Dashboard original
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent root = loader.load();

            // 2. Cria a janela do Dashboard com o tamanho original que você definiu (1250x650)
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Logística de Carga - Menu Clientes");
            dashboardStage.setScene(new Scene(root, 1250, 650));
            dashboardStage.show();

            // 3. Fecha a janela de login atual
            Stage loginStage = (Stage) txtUsuario.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            System.err.println("Erro ao carregar o Dashboard principal.");
            e.printStackTrace();
        }
    }
}