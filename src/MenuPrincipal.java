import java.sql.*;
import java.util.Scanner;

public class MenuPrincipal {
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
                        3| Gerenciamento de produtos
                        4| Gerenciamento de pedidos
                        5| Gerenciamento de itens e pedidos
                        6| Gerenciamento de veículos
                        7| Gerenciamento de Cargas
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
                    case 3:
                        new Produto().executarProduto(conexao);
                        break;
                    case 4:
                        new Pedido().executarPedido(conexao);
                        break;
                    case 5:
                        new ItensPedido().executarItensPedido(conexao);
                        break;
                    case 6:
                        new Veiculo().executarVeiculo(conexao);
                        break;
                    case 7:
                        new Carga().executarCarga(conexao);
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