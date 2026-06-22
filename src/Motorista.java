import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Motorista {
    public void executarMotorista(Connection conexao) throws Exception{
        Scanner sc = new Scanner(System.in);

        int opcaoM;

        do {
            System.out.println("""
                        
                        ====MENU DO MOTORISTA======
                        1| Cadastrar Motorista
                        2| Listar Motoristas
                        3| Atualizar dados do Motorista
                        4| Deletar Motorista
                        0| Sair
                        
                        Escolha uma opção: 
                        """);
            opcaoM = sc.nextInt();
            sc.nextLine();

            switch (opcaoM){
                case 1:

                    System.out.println("\n===Cadastrar Motorista===");

                    System.out.print("Digite o nome do Motorista: ");
                    String nome = sc.nextLine();
                    System.out.print("Digite a CNH do Motorista: ");
                    String cnh = sc.nextLine();
                    System.out.print("Digite a Categoria da CNH: ");
                    String categoria = sc.nextLine();

                    String insert = "INSERT INTO motorista (nome, cnh, categoria_cnh) VALUES (?,?,?)";


                    PreparedStatement psInsert = conexao.prepareStatement(insert);
                    psInsert.setString(1, nome);
                    psInsert.setString(2, cnh);
                    psInsert.setString(3, categoria);
                    psInsert.executeUpdate();
                    System.out.println("Motorista cadastrado com sucesso!");
                    break;
                case 2:
                    String select = "SELECT * FROM motorista";
                    Statement stat = conexao.createStatement();
                    ResultSet rs = stat.executeQuery(select);

                    System.out.print("""
                                =====Motoristas====
                                ID | Nome | CNH | Categoria de CNH
                                """);
                    while(rs.next()) {
                        System.out.printf("%d - %s - %s - %s%n",
                                rs.getInt("id"),
                                rs.getString("nome"),
                                rs.getString("cnh"),
                                rs.getString("categoria_cnh"));
                    }
                    break;
                case 3:
                    int opcaoUpd;

                    System.out.print("""
                                =====ATUALIZAÇÃO DE DADOS=====
                                - O que você deseja atualizar?
                                1| Nome
                                2| CNH
                                3| Categoria de CNH
                                4| Todos
                                """);
                    opcaoUpd = sc.nextInt();
                    sc.nextLine();

                    switch (opcaoUpd){
                        case 1:
                            System.out.print("Digite o ID do Motorista que deseja atualizar: ");
                            int idUpdateN = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo nome: ");
                            String nomeUpdate = sc.nextLine();

                            String updateN = "UPDATE motorista SET nome = ? WHERE id = ?";

                            PreparedStatement psNameUpdate = conexao.prepareStatement(updateN);
                            psNameUpdate.setString(1, nomeUpdate);
                            psNameUpdate.setInt(2, idUpdateN);
                            psNameUpdate.executeUpdate();
                            System.out.println("Nome Atualizado com sucesso!");
                            break;
                        case 2:
                            System.out.print("Digite o ID do Motorista que deseja atualizar: ");
                            int idUpdateCnh = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo CNH: ");
                            String cnhUpdate = sc.nextLine();

                            String updateC = "UPDATE motorista SET cnh = ? WHERE id = ?";

                            PreparedStatement psCnhUpdate = conexao.prepareStatement(updateC);
                            psCnhUpdate.setString(1, cnhUpdate);
                            psCnhUpdate.setInt(2, idUpdateCnh);
                            psCnhUpdate.executeUpdate();
                            System.out.println("CNH Atualizado com sucesso!");
                            break;
                        case 3:
                            System.out.print("Digite o ID do Motorista que deseja atualizar: ");
                            int idUpdateCat = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova categoria de CNH: ");
                            String catUpdate = sc.nextLine();

                            String updateE = "UPDATE motorista SET categoria_cnh = ? WHERE id = ?";

                            PreparedStatement psCatelUpdate = conexao.prepareStatement(updateE);
                            psCatelUpdate.setString(1, catUpdate);
                            psCatelUpdate.setInt(2, idUpdateCat);
                            psCatelUpdate.executeUpdate();
                            System.out.println("Categoria de CNH atualizada com sucesso!");
                            break;
                        case 4:
                            System.out.print("ID do Motorista que deseja atualizar: ");
                            int idUpdate2 = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Novo Nome: ");
                            String nomeUpdate2 = sc.nextLine();
                            System.out.print("Novo CNH: ");
                            String cnhUpdate2 = sc.nextLine();
                            System.out.print("Nova Categoria de CNH: ");
                            String cateUpdate2 = sc.nextLine();


                            String update = "UPDATE motorista SET nome = ?, cnh = ?, categoria_cnh = ? Where id = ?";

                            PreparedStatement psUpdate = conexao.prepareStatement(update);
                            psUpdate.setString(1, nomeUpdate2);
                            psUpdate.setString(2, cnhUpdate2);
                            psUpdate.setString(3, cateUpdate2);
                            psUpdate.setInt(4, idUpdate2);
                            psUpdate.executeUpdate();
                            System.out.println("Motorista Atualizado com sucesso!");
                            break;
                        default:
                            System.out.println("Opção Inválida!");

                    }
                    break;
                case 4:
                    System.out.print("Selecione um ID do Motorista para deletar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();

                    String delete = "DELETE FROM motorista WHERE id = ?";

                    PreparedStatement psDelete = conexao.prepareStatement(delete);
                    psDelete.setInt(1, idDelete);
                    psDelete.executeUpdate();
                    System.out.println();
                    System.out.println("Motorista deletado!");
                    break;
                case 0:
                    System.out.println("\nGerenciamento de Motoristas concluído!");
                    break;
                default:
                    System.out.println("Selecione uma opção válida!");
            }

        } while (opcaoM != 0);

    }
}