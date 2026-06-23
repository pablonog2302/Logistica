import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Produto {
    public void executarProduto(Connection conexao) throws Exception{
        Scanner sc = new Scanner(System.in);

        int opcaoP;

        do {
            System.out.println("""
                        
                        ====MENU DO PRODUTO======
                        1| Cadastrar Produto
                        2| Listar Produtos
                        3| Atualizar dados dos Produtos
                        4| Deletar Produto
                        0| Voltar
                        
                        Escolha uma opção: 
                        """);
            opcaoP = sc.nextInt();
            sc.nextLine();

            switch (opcaoP){
                case 1:

                    System.out.println("\n===Cadastrar Produto===");

                    System.out.print("Digite o nome do Produto: ");
                    String nome = sc.nextLine();
                    System.out.print("Digite a largura do Produto: ");
                    double largura= sc.nextDouble();
                    System.out.print("Digite a altura do Produto: ");
                    double altura = sc.nextDouble();
                    System.out.print("Digite o Empilhamento Máximo: ");
                    int empMaximo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("OBS (Opcional): ");
                    String obs = sc.nextLine();

                    String insert = "INSERT INTO produto (nome, largura, altura, empilhamento_maximo, obs) VALUES (?,?,?,?,?)";


                    PreparedStatement psInsert = conexao.prepareStatement(insert);
                    psInsert.setString(1, nome);
                    psInsert.setDouble(2, largura);
                    psInsert.setDouble(3, altura);
                    psInsert.setInt(4, empMaximo);
                    psInsert.setString(5, obs);
                    psInsert.executeUpdate();
                    System.out.println("Produto cadastrado com sucesso!");
                    break;
                case 2:
                    String select = "SELECT * FROM produto";
                    Statement stat = conexao.createStatement();
                    ResultSet rs = stat.executeQuery(select);

                    System.out.print("""
                                ============Produtos==========
                                ID | Produto | Largura | Altura | Emplilhamento Máximo | Obs
                                """);
                    while(rs.next()) {
                        System.out.printf("%d - %s - %.2f - %.2f - %d - %s%n",
                                rs.getInt("id"),
                                rs.getString("nome"),
                                rs.getDouble("largura"),
                                rs.getDouble("altura"),
                                rs.getInt("empilhamento_maximo"),
                                rs.getString("obs"));
                    }
                    break;
                case 3:
                    int opcaoUpd;

                    System.out.print("""
                                =====ATUALIZAÇÃO DE DADOS=====
                                - O que você deseja atualizar?
                                1| Nome do Produto
                                2| Largura
                                3| Altura
                                4| Empilhamento Máximo
                                5| Obs
                                6| Todos os dados
                                """);
                    opcaoUpd = sc.nextInt();
                    sc.nextLine();

                    switch (opcaoUpd){
                        case 1:
                            System.out.print("Digite o ID do Produto que deseja atualizar: ");
                            int idUpdateN = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo nome: ");
                            String nomeUpdate = sc.nextLine();

                            String updateN = "UPDATE produto SET nome = ? WHERE id = ?";

                            PreparedStatement psNameUpdate = conexao.prepareStatement(updateN);
                            psNameUpdate.setString(1, nomeUpdate);
                            psNameUpdate.setInt(2, idUpdateN);
                            psNameUpdate.executeUpdate();
                            System.out.println("Nome Atualizado com sucesso!");
                            break;
                        case 2:
                            System.out.print("Digite o ID do Produto que deseja atualizar: ");
                            int idUpdateL = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova Largura: ");
                            double largUpdate = sc.nextDouble();

                            String updateC = "UPDATE produto SET largura = ? WHERE id = ?";

                            PreparedStatement psLargUpdate = conexao.prepareStatement(updateC);
                            psLargUpdate.setDouble(1, largUpdate);
                            psLargUpdate.setInt(2, idUpdateL);
                            psLargUpdate.executeUpdate();
                            System.out.println("Largura Atualizada com sucesso!");
                            break;
                        case 3:
                            System.out.print("Digite o ID do Produto que deseja atualizar: ");
                            int idUpdateA = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova Altura: ");
                            double altUpdate = sc.nextDouble();

                            String updateA = "UPDATE produto SET altura = ? WHERE id = ?";

                            PreparedStatement psAltUpdate = conexao.prepareStatement(updateA);
                            psAltUpdate.setDouble(1, altUpdate);
                            psAltUpdate.setInt(2, idUpdateA);
                            psAltUpdate.executeUpdate();
                            System.out.println("Altura atualizada com sucesso!");
                            break;
                        case 4:
                            System.out.print("Digite o ID do Produto que deseja atualizar: ");
                            int idUpdateE = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Empilhamento Máximo: ");
                            int empUpdate = sc.nextInt();

                            String updateE = "UPDATE produto SET empilhamento_maximo = ? WHERE id = ?";

                            PreparedStatement psEmpUpdate = conexao.prepareStatement(updateE);
                            psEmpUpdate.setInt(1, empUpdate);
                            psEmpUpdate.setInt(2, idUpdateE);
                            psEmpUpdate.executeUpdate();
                            System.out.println("Empilhamento Máximo atualizado com sucesso!");
                            break;
                        case 5:
                            System.out.print("Digite o ID do Produto que deseja atualizar: ");
                            int idUpdateO = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova Obs: ");
                            String obsUpdate = sc.nextLine();

                            String updateO = "UPDATE produto SET obs = ? WHERE id = ?";

                            PreparedStatement psObsUpdate = conexao.prepareStatement(updateO);
                            psObsUpdate.setString(1, obsUpdate);
                            psObsUpdate.setInt(2, idUpdateO);
                            psObsUpdate.executeUpdate();
                            System.out.println("Obs atualizada com sucesso!");
                            break;

                        case 6:
                            System.out.print("ID do Produto que deseja atualizar: ");
                            int idUpdate2 = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Novo Nome: ");
                            String nomeUpdate2 = sc.nextLine();
                            System.out.print("Nova Largura: ");
                            double largUpdate2 = sc.nextDouble();
                            System.out.print("Nova Altura: ");
                            double altUpdate2 = sc.nextDouble();
                            System.out.print("Novo Empilhamento Máximo: ");
                            int empUpdate2 = sc.nextInt();
                            System.out.print("Nova Obs: ");
                            String obsUpdate2 = sc.nextLine();

                            String update = "UPDATE cliente SET nome = ?, largura = ?, altura = ?, empilhamento_maximo = ?, obs = ? Where id = ?";

                            PreparedStatement psUpdate = conexao.prepareStatement(update);
                            psUpdate.setString(1, nomeUpdate2);
                            psUpdate.setDouble(2, largUpdate2);
                            psUpdate.setDouble(3, altUpdate2);
                            psUpdate.setInt(4, empUpdate2);
                            psUpdate.setString(5, obsUpdate2);
                            psUpdate.setInt(6, idUpdate2);
                            psUpdate.executeUpdate();
                            System.out.println("Produto Atualizado com sucesso!");
                            break;
                        default:
                            System.out.println("Opção Inválida!");

                    }
                    break;
                case 4:
                    System.out.print("Selecione um ID para deletar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();

                    String delete = "DELETE FROM produto WHERE id = ?";

                    PreparedStatement psDelete = conexao.prepareStatement(delete);
                    psDelete.setInt(1, idDelete);
                    psDelete.executeUpdate();
                    System.out.println();
                    System.out.println("Produto deletado!");
                    break;
                case 0:
                    System.out.println("\nGerenciamento de produtos concluído!");
                    break;
                default:
                    System.out.println("Selecione uma opção válida!");
            }

        } while (opcaoP != 0);



    }
}