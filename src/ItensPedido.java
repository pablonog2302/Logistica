import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ItensPedido {
    public void executarItensPedido(Connection conexao) throws Exception{
        Scanner sc = new Scanner(System.in);

        int opcaoC;

        do {
            System.out.println("""
                        
                        ====MENU DE ITENS E PEDIDOS======
                        1| Cadastrar Itens e Pedidos
                        2| Listar Itens e Pedidos
                        3| Atualizar dados de Itens e Pedidos
                        4| Deletar Itens e Pedidos
                        0| Voltar
                        
                        Escolha uma opção: 
                        """);
            opcaoC = sc.nextInt();
            sc.nextLine();

            switch (opcaoC){
                case 1:

                    System.out.println("\n===Cadastrar Itens e Pedidos===");

                    System.out.print("Digite a Quantidade: ");
                    int quantidade = sc.nextInt();
                    System.out.print("Digite o peso total do Produto: ");
                    double pesoTotal = sc.nextDouble();
                    System.out.print("Digite o ID do Pedido: ");
                    int idPedido = sc.nextInt();
                    System.out.print("Digite o ID do Produto: ");
                    int idProduto = sc.nextInt();


                    String insert = "INSERT INTO itens_pedido (quantidade, peso_total_item, pedido_id, produto_id) VALUES (?,?,?,?)";


                    PreparedStatement psInsert = conexao.prepareStatement(insert);
                    psInsert.setInt(1, quantidade);
                    psInsert.setDouble(2, pesoTotal);
                    psInsert.setInt(3, idPedido);
                    psInsert.setInt(4, idProduto);
                    psInsert.executeUpdate();
                    System.out.println("Itens e Pedidos cadastrados com sucesso!");
                    break;
                case 2:
                    String select = "SELECT * FROM itens_pedido";
                    Statement stat = conexao.createStatement();
                    ResultSet rs = stat.executeQuery(select);

                    System.out.print("""
                                =====Itens e Pedidos====
                                ID | Quantidade | Peso Total do Produto | Pedido | Produto
                                """);
                    while(rs.next()) {
                        System.out.printf("%d - %d - %.2f - %d - %d%n",
                                rs.getInt("id"),
                                rs.getInt("quantidade"),
                                rs.getDouble("peso_total_item"),
                                rs.getInt("pedido_id"),
                                rs.getInt("produto_id"));
                    }
                    break;
                case 3:
                    int opcaoUpd;

                    System.out.print("""
                                =====ATUALIZAÇÃO DE DADOS=====
                                - O que você deseja atualizar?
                                1| Quantidade
                                2| Peso Total do Produto
                                3| Pedido
                                4| Produto
                                5| Todos os dados
                                0| Voltar
                                """);
                    opcaoUpd = sc.nextInt();
                    sc.nextLine();

                    switch (opcaoUpd){
                        case 1:
                            System.out.print("Digite o ID dos Itens e Pedidos que deseja atualizar: ");
                            int idUpdateQuantidade = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova Quantidade: ");
                            int quantUpdate = sc.nextInt();

                            String updateQuantidade = "UPDATE itens_pedido SET quantidade = ? WHERE id = ?";

                            PreparedStatement psNameUpdate = conexao.prepareStatement(updateQuantidade);
                            psNameUpdate.setInt(1, quantUpdate);
                            psNameUpdate.setInt(2, idUpdateQuantidade);
                            psNameUpdate.executeUpdate();
                            System.out.println("Quantidade Atualizada com sucesso!");
                            break;
                        case 2:
                            System.out.print("Digite o ID dos Itens e Pedidos que deseja atualizar: ");
                            int idUpdatePeso = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Peso Total: ");
                            double pesoUpdate = sc.nextDouble();

                            String updatePeso = "UPDATE itens_pedido SET peso_total_item = ? WHERE id = ?";

                            PreparedStatement psPesoUpdate = conexao.prepareStatement(updatePeso);
                            psPesoUpdate.setDouble(1, pesoUpdate);
                            psPesoUpdate.setInt(2, idUpdatePeso);
                            psPesoUpdate.executeUpdate();
                            System.out.println("Peso Total Atualizado com sucesso!");
                            break;
                        case 3:
                            System.out.print("Digite o ID dos Itens e Pedidos que deseja atualizar: ");
                            int idUpdatePedido = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite o novo Pedido: ");
                            int pedidoUpdate = sc.nextInt();

                            String updatePedido = "UPDATE itens_pedido SET pedido_id = ? WHERE id = ?";

                            PreparedStatement psPedidoUpdate = conexao.prepareStatement(updatePedido);
                            psPedidoUpdate.setInt(1, pedidoUpdate);
                            psPedidoUpdate.setInt(2, idUpdatePedido);
                            psPedidoUpdate.executeUpdate();
                            System.out.println("Pedido atualizado com sucesso!");
                            break;
                        case 4:
                            System.out.print("Digite o ID dos Itens e Pedidos que deseja atualizar: ");
                            int idUpdateProduto = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite o novo Produto: ");
                            int produtoUpdate = sc.nextInt();

                            String updateProduto = "UPDATE itens_pedido SET produto_id = ? WHERE id = ?";

                            PreparedStatement psProdutoUpdate = conexao.prepareStatement(updateProduto);
                            psProdutoUpdate.setInt(1, produtoUpdate);
                            psProdutoUpdate.setInt(2, idUpdateProduto);
                            psProdutoUpdate.executeUpdate();
                            System.out.println("Produto atualizado com sucesso!");
                            break;
                        case 5:
                            System.out.print("ID dos Itens e Pedidos que deseja atualizar: ");
                            int idUpdate = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova Quantidade: ");
                            int quantUpdate2 = sc.nextInt();
                            System.out.print("Digite um novo Peso Total: ");
                            double pesoUpdate2 = sc.nextDouble();
                            System.out.print("Digite o novo Pedido: ");
                            int pedidoUpdate2 = sc.nextInt();
                            System.out.print("Digite o novo Produto: ");
                            int produtoUpdate2 = sc.nextInt();


                            String update = "UPDATE itens_pedido SET quantidade = ?, peso_total_item = ?, pedido_id = ?, produto_id = ? Where id = ?";

                            PreparedStatement psUpdate = conexao.prepareStatement(update);
                            psUpdate.setInt(1, quantUpdate2);
                            psUpdate.setDouble(2, pesoUpdate2);
                            psUpdate.setInt(3, pedidoUpdate2);
                            psUpdate.setInt(4, produtoUpdate2);
                            psUpdate.setInt(5, idUpdate);
                            psUpdate.executeUpdate();
                            System.out.println("Itens e Pedidos Atualizados com sucesso!");
                            break;
                        default:
                            System.out.println("Opção Inválida!");

                    }
                    break;
                case 4:
                    System.out.print("Selecione um ID para deletar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();

                    String delete = "DELETE FROM itens_pedido WHERE id = ?";

                    PreparedStatement psDelete = conexao.prepareStatement(delete);
                    psDelete.setInt(1, idDelete);
                    psDelete.executeUpdate();
                    System.out.println();
                    System.out.println("Itens e Pedidos deletados!");
                    break;
                case 0:
                    System.out.println("\nGerenciamento de itens e pedidos concluído!");
                    break;
                default:
                    System.out.println("Selecione uma opção válida!");
            }

        } while (opcaoC != 0);



    }
}