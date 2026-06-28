package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializa apontando para a tela de LOGIN
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

        // Janela de login menor
        Scene scene = new Scene(loader.load(), 350, 400);
        primaryStage.setTitle("Logística de Carga - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}