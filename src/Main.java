import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/logistica";
        String user = "root";
        String password = "";

        try{
            Connection conexao = DriverManager.getConnection(url, user, password);

            System.out.println("\nConectado!\n");
            int opcao;

            do {
                System.out.println("""
                        
                        ======MENU PRINCIPAL=====
                        1| Gerenciamento de clientes
                        2| Gerenciamento de motoristas
                        0| Sair
                        
                        Escolha uma opção: 
                        """);
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        new Cliente().executarCliente(conexao);
                        break;
                    case 2:
                        new Motorista().executarMotorista(conexao);
                        break;
                    case 0:
                        System.out.println("Sistema Finalizado!");
                        break;
                    default:
                        System.out.println("Digite uma opção válida!");
                }
            } while (opcao != 0);


        }catch (Exception e){
            e.printStackTrace();
        }
        sc.close();

    }
}