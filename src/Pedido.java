import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Pedido {
    public void executarPedido(Connection conexao) throws Exception{
        Scanner sc = new Scanner(System.in);

        int opcaoPe;

        do {
            System.out.println("""
                        
                        ====MENU DOS PEDIDOS======
                        1| Cadastrar Pedido
                        2| Listar Pedidos
                        3| Atualizar dados dos Pedidos
                        4| Deletar Pedido
                        0| Voltar
                        
                        Escolha uma opção: 
                        """);
            opcaoPe = sc.nextInt();
            sc.nextLine();

            switch (opcaoPe){
                case 1: //Cadastro

                    System.out.println("\n===Cadastrar Pedido===");

                    System.out.print("Digite a data de emissão do Pedido (dd/MM/yyyy): ");
                    String dataTexto = sc.nextLine();
                    DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(dataTexto, formatoBr);

                    System.out.print("Digite o id do Cliente Remetente: ");
                    int idCliReme = sc.nextInt();
                    System.out.print("Digite o id do Cliente Destinatário: ");
                    int idCliDest = sc.nextInt();
                    System.out.print("""
                            Digite o id do Status: 
                            1| Pendente
                            2| Em processo
                            3| Entregue
                            """);
                    int idStatus = sc.nextInt();

                    String insert = "INSERT INTO pedido (data_emissao, cliente_remetente_id, cliente_destinatario_id, status_id) VALUES (?,?,?,?)";


                    PreparedStatement psInsert = conexao.prepareStatement(insert);
                    psInsert.setDate(1, java.sql.Date.valueOf(data));
                    psInsert.setInt(2,idCliReme);
                    psInsert.setInt(3,idCliDest);
                    psInsert.setInt(4,idStatus);
                    psInsert.executeUpdate();
                    System.out.println("Pedido cadastrado com sucesso!");
                    break;
                case 2: //Lista
                    String select = "SELECT * FROM pedido";
                    Statement stat = conexao.createStatement();
                    ResultSet rs = stat.executeQuery(select);

                    System.out.print("""
                                =====Pedidos====
                                ID | Data de emissão | Cliente Remetente | Cliente Destinatário | Status
                                """);
                    while(rs.next()) {
                        System.out.printf("%d - %s - %d - %d - %d%n",
                                rs.getInt("id"),
                                rs.getDate("data_emissao").toLocalDate(),
                                rs.getInt("cliente_remetente_id"),
                                rs.getInt("cliente_destinatario_id"),
                                rs.getInt("status_id"));
                    }
                    break;
                case 3: //Updates
                    int opcaoUpd;

                    System.out.print("""
                                =====ATUALIZAÇÃO DE DADOS=====
                                - O que você deseja atualizar?
                                1| Data de Emissão
                                2| Cliente Remetente
                                3| Cliente Destinatário
                                4| Status
                                5| Todos os dados
                                0| Voltar
                                """);
                    opcaoUpd = sc.nextInt();
                    sc.nextLine();

                    switch (opcaoUpd){
                        case 1:
                            System.out.print("Digite o ID do Pedido que deseja atualizar: ");
                            int idUpdateData = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite a data de emissão do Pedido (dd/MM/yyyy): ");
                            String dataTextoUpd = sc.nextLine();
                            DateTimeFormatter formatoBrUpd = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate dataUpd = LocalDate.parse(dataTextoUpd, formatoBrUpd);

                            String updateData = "UPDATE pedido SET data_emissao = ? WHERE id = ?";

                            PreparedStatement psDataUpdate = conexao.prepareStatement(updateData);
                            psDataUpdate.setDate(1, java.sql.Date.valueOf(dataUpd));
                            psDataUpdate.setInt(2, idUpdateData);
                            psDataUpdate.executeUpdate();
                            System.out.println("Data Atualizada com sucesso!");
                            break;
                        case 2:
                            System.out.print("Digite o ID do Pedido que deseja atualizar: ");
                            int idUpdateReme = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Cliente Remetente: ");
                            int remeUpdate = sc.nextInt();

                            String updateReme = "UPDATE cliente SET cliente_remetente_id = ? WHERE id = ?";

                            PreparedStatement psRemeUpdate = conexao.prepareStatement(updateReme);
                            psRemeUpdate.setInt(1, remeUpdate);
                            psRemeUpdate.setInt(2, idUpdateReme);
                            psRemeUpdate.executeUpdate();
                            System.out.println("Cliente Remetente Atualizado com sucesso!");
                            break;
                        case 3:
                            System.out.print("Digite o ID do Pedido que deseja atualizar: ");
                            int idUpdateDest = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Cliente Destinatário: ");
                            int destUpdate = sc.nextInt();

                            String updateDest = "UPDATE cliente SET cliente_destinatario_id = ? WHERE id = ?";

                            PreparedStatement psDestUpdate = conexao.prepareStatement(updateDest);
                            psDestUpdate.setInt(1, destUpdate);
                            psDestUpdate.setInt(2, idUpdateDest);
                            psDestUpdate.executeUpdate();
                            System.out.println("Cliente Destinatário atualizado com sucesso!");
                            break;
                        case 4:
                            System.out.print("Digite o ID do Pedido que deseja atualizar: ");
                            int idUpdateSts = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Status: ");
                            int stsUpdate = sc.nextInt();

                            String updateSts = "UPDATE pedido SET status_id = ? WHERE id = ?";

                            PreparedStatement psStsUpdate = conexao.prepareStatement(updateSts);
                            psStsUpdate.setInt(1, stsUpdate);
                            psStsUpdate.setInt(2, idUpdateSts);
                            psStsUpdate.executeUpdate();
                            System.out.println("Status atualizado com sucesso!");
                            break;
                        case 5:
                            System.out.print("ID do Pedido que deseja atualizar: ");
                            int idUpdate = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite a data de emissão do Pedido (dd/MM/yyyy): ");
                            String dataTextoUpd2 = sc.nextLine();
                            DateTimeFormatter formatoBrUpd2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate dataUpd2 = LocalDate.parse(dataTextoUpd2, formatoBrUpd2);
                            System.out.print("Novo Cliente Remetente: ");
                            int remeUpdate2 = sc.nextInt();
                            System.out.print("Novo Cliente Destinatário: ");
                            int destUpdate2 = sc.nextInt();
                            System.out.print("Novo Status: ");
                            int stsUpdate2 = sc.nextInt();


                            String update = "UPDATE pedido SET data_emissao = ?, cliente_remetente_id = ?, cliente_destinatario_id = ?, status_id Where id = ?";

                            PreparedStatement psUpdate = conexao.prepareStatement(update);
                            psUpdate.setDate(1, java.sql.Date.valueOf(dataUpd2));
                            psUpdate.setInt(2, remeUpdate2);
                            psUpdate.setInt(3, destUpdate2);
                            psUpdate.setInt(4, stsUpdate2);
                            psUpdate.setInt(5, idUpdate);
                            psUpdate.executeUpdate();
                            System.out.println("Pedido Atualizado com sucesso!");
                            break;
                        default:
                            System.out.println("Opção Inválida!");

                    }
                    break;
                case 4:
                    System.out.print("Selecione um ID para deletar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();

                    String delete = "DELETE FROM pedido WHERE id = ?";

                    PreparedStatement psDelete = conexao.prepareStatement(delete);
                    psDelete.setInt(1, idDelete);
                    psDelete.executeUpdate();
                    System.out.println();
                    System.out.println("Pedido deletado!");
                    break;
                case 0:
                    System.out.println("\nGerenciamento de pedidos concluído!");
                    break;
                default:
                    System.out.println("Selecione uma opção válida!");
            }

        } while (opcaoPe != 0);



    }
}