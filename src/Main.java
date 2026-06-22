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
                        
                        ====MENU====
                        
                        1 - Cadastrar Usuário
                        2 - Listar Usuários
                        3 - Atualizar dados do Usuários
                        4 - Deletar Usuário
                        5 - Sair
                        
                        Escolha uma opção: 
                        """);
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1:

                        System.out.println("\n===Cadastrar Usuário===");

                        System.out.print("Digite o nome do Usuário: ");
                        String nome = sc.nextLine();
                        System.out.print("Digite o cpf do Usuário: ");
                        String cpf = sc.nextLine();
                        System.out.print("Digite o email do Usuário: ");
                        String email = sc.nextLine();

                        String insert = "INSERT INTO cliente (nome, cpf, email) VALUES (?,?,?)";

                        PreparedStatement psInsert = conexao.prepareStatement(insert);
                        psInsert.setString(1, nome);
                        psInsert.setString(2, cpf);
                        psInsert.setString(3, email);
                        psInsert.executeUpdate();
                        System.out.println("Usuário cadastrado com sucesso!");
                        break;
                    case 2:
                        String select = "SELECT * FROM cliente";
                        Statement stat = conexao.createStatement();
                        ResultSet rs = stat.executeQuery(select);

                        System.out.print("""
                                =====Usuários====
                                ID | Nome | CPF | Email
                                """);
                        while(rs.next()) {
                            System.out.printf("%d - %s - %s - %s%n",
                                    rs.getInt("id"),
                                    rs.getString("nome"),
                                    rs.getString("cpf"),
                                    rs.getString("email"));
                        }
                        break;
                    case 3:
                        int opcaoUpd;

                        System.out.print("""
                                O que você deseja atualizar?
                                1 - nome
                                2 - cpf
                                3 - email
                                4 - Todos
                                """);
                        opcaoUpd = sc.nextInt();
                        sc.nextLine();

                        switch (opcaoUpd){
                            case 1:
                                System.out.print("Digite o ID do Usuário que deseja atualizar: ");
                                int idUpdateN = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Digite um novo nome: ");
                                String nomeUpdate = sc.nextLine();

                                String updateN = "UPDATE cliente SET nome = ? WHERE id = ?";

                                PreparedStatement psNameUpdate = conexao.prepareStatement(updateN);
                                psNameUpdate.setString(1, nomeUpdate);
                                psNameUpdate.setInt(2, idUpdateN);
                                psNameUpdate.executeUpdate();
                                System.out.println("Nome Atualizado com sucesso!");
                                break;
                            case 2:
                                System.out.print("Digite o ID do Usuário que deseja atualizar: ");
                                int idUpdateC = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Digite um novo CPF: ");
                                String cpfUpdate = sc.nextLine();

                                String updateC = "UPDATE cliente SET cpf = ? WHERE id = ?";

                                PreparedStatement psCpfUpdate = conexao.prepareStatement(updateC);
                                psCpfUpdate.setString(1, cpfUpdate);
                                psCpfUpdate.setInt(2, idUpdateC);
                                psCpfUpdate.executeUpdate();
                                System.out.println("CPF Atualizado com sucesso!");
                                break;
                            case 3:
                                System.out.print("Digite o ID do Usuário que deseja atualizar: ");
                                int idUpdateE = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Digite um novo Email: ");
                                String emailUpdate = sc.nextLine();

                                String updateE = "UPDATE cliente SET email = ? WHERE id = ?";

                                PreparedStatement psEmailUpdate = conexao.prepareStatement(updateE);
                                psEmailUpdate.setString(1, emailUpdate);
                                psEmailUpdate.setInt(2, idUpdateE);
                                psEmailUpdate.executeUpdate();
                                System.out.println("Email atualizado com sucesso!");
                                break;
                            case 4:
                                System.out.print("ID do usuário que deseja atualizar: ");
                                int idUpdate2 = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Novo Nome: ");
                                String nomeUpdate2 = sc.nextLine();
                                System.out.print("Novo CPF: ");
                                String cpfUpdate2 = sc.nextLine();
                                System.out.print("Novo Email: ");
                                String emailUpdate2 = sc.nextLine();


                                String update = "UPDATE cliente SET nome = ?, cpf = ?, email = ? Where id = ?";

                                PreparedStatement psUpdate = conexao.prepareStatement(update);
                                psUpdate.setString(1, nomeUpdate2);
                                psUpdate.setString(2, cpfUpdate2);
                                psUpdate.setString(3, emailUpdate2);
                                psUpdate.setInt(4, idUpdate2);
                                psUpdate.executeUpdate();
                                System.out.println("Usuário Atualizado com sucesso!");
                                break;
                            default:
                                System.out.println("Opção Inválida!");

                        }
                        break;
                    case 4:
                        System.out.print("Selecione um ID para deletar: ");
                        int idDelete = sc.nextInt();
                        sc.nextLine();

                        String delete = "DELETE FROM cliente WHERE id = ?";

                        PreparedStatement psDelete = conexao.prepareStatement(delete);
                        psDelete.setInt(1, idDelete);
                        psDelete.executeUpdate();
                        System.out.println();
                        System.out.println("Usuário deletado!");
                        break;
                    case 5:
                        System.out.println("\nSistema Finalizado!");
                        break;
                    default:
                        System.out.println("Selecione uma opção válida!");
                }

            } while (opcao != 5);


        }catch (Exception e){
            e.printStackTrace();
        }

        sc.close();

    }
}