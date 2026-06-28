import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Carga {
    public void executarCarga(Connection conexao) throws Exception{
        Scanner sc = new Scanner(System.in);

        int opcaoCarga;

        do {
            System.out.println("""
                        
                        ====MENU DE CARGA======
                        1| Cadastrar Carga
                        2| Listar Cargas
                        3| Atualizar dados da Carga
                        4| Deletar Carga
                        0| Voltar
                        
                        Escolha uma opção: 
                        """);
            opcaoCarga = sc.nextInt();
            sc.nextLine();

            switch (opcaoCarga){
                case 1:

                    System.out.println("\n===Cadastrar Carga===");

                    System.out.print("Digite a data de partida (dd/MM/yyyy): ");
                    String dataTexto = sc.nextLine();
                    DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(dataTexto, formatoBr);
                    System.out.print("Digiteo volume total ocupado: ");
                    double volume = sc.nextDouble();
                    System.out.print("Digite o peso total carregado: ");
                    double peso = sc.nextDouble();
                    System.out.print("Digite o ID do Veículo");
                    int veiculoId = sc.nextInt();
                    System.out.print("Digite o ID do Motorista");
                    int motoristaId = sc.nextInt();
                    System.out.print("Digite o ID do Pedido");
                    int pedidoId = sc.nextInt();

                    String insert = "INSERT INTO manifestos_carga (data_partida, volume_total_ocupado, peso_total_carregado, veiculo_id, motorista_id, pedido_id) VALUES (?,?,?,?,?,?)";

                    PreparedStatement psInsert = conexao.prepareStatement(insert);
                    psInsert.setDate(1, java.sql.Date.valueOf(data));
                    psInsert.setDouble(2, volume);
                    psInsert.setDouble(3, peso);
                    psInsert.setInt(4, veiculoId);
                    psInsert.setInt(5, motoristaId);
                    psInsert.setInt(6, pedidoId);
                    psInsert.executeUpdate();
                    System.out.println("Carga cadastrada com sucesso!");
                    break;
                case 2:
                    String select = "SELECT * FROM manifestos_carga";
                    Statement stat = conexao.createStatement();
                    ResultSet rs = stat.executeQuery(select);

                    System.out.print("""
                                ==========Cargas=========
                                ID | Data de Partida | Volume Total Ocupado | Peso Total Carregado | Veículo | Motorista | Pedido
                                """);
                    while(rs.next()) {
                        System.out.printf("%d - %s - %.2fm - %.2fkg - %d - %d - %d%n",
                                rs.getInt("id"),
                                rs.getDate("data_partida").toLocalDate(),
                                rs.getDouble("volume_total_ocupado"),
                                rs.getDouble("peso_total_carregado_kg"),
                                rs.getInt("veiculo_id"),
                                rs.getInt("motorista_id"),
                                rs.getInt("pedido_id"));
                    }
                    break;
                case 3:
                    int opcaoUpd;

                    System.out.print("""
                                =====ATUALIZAÇÃO DE DADOS=====
                                - O que você deseja atualizar?
                                1| Data de Partida
                                2| Volume Total Ocupado
                                3| Peso Total Carregado
                                4| Veículo
                                5| Motorista
                                6| Pedido
                                7| Todos
                                """);
                    opcaoUpd = sc.nextInt();
                    sc.nextLine();

                    switch (opcaoUpd){
                        case 1:
                            System.out.print("Digite o ID da Carga que deseja atualizar: ");
                            int idUpdateData = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova data: ");
                            String dataTextoUpd = sc.nextLine();
                            DateTimeFormatter formatoBrUpd = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate dataUpd = LocalDate.parse(dataTextoUpd, formatoBrUpd);

                            String updateData = "UPDATE manifestos_carga SET data_partida = ? WHERE id = ?";

                            PreparedStatement psDataUpdate = conexao.prepareStatement(updateData);
                            psDataUpdate.setDate(1, java.sql.Date.valueOf(dataUpd));
                            psDataUpdate.setInt(2, idUpdateData);
                            psDataUpdate.executeUpdate();
                            System.out.println("Data Atualizada com sucesso!");
                            break;
                        case 2:
                            System.out.print("Digite o ID da Carga que deseja atualizar: ");
                            int idUpdateVolume = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Volume: ");
                            double volumeUpdate = sc.nextDouble();

                            String updateVolume = "UPDATE manifestos_carga SET volume_total_ocupado = ? WHERE id = ?";

                            PreparedStatement psVolumeUpdate = conexao.prepareStatement(updateVolume);
                            psVolumeUpdate.setDouble(1, volumeUpdate);
                            psVolumeUpdate.setInt(2, idUpdateVolume);
                            psVolumeUpdate.executeUpdate();
                            System.out.println("Volume Atualizado com sucesso!");
                            break;
                        case 3:
                            System.out.print("Digite o ID da Carga que deseja atualizar: ");
                            int idUpdatePeso = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Peso: ");
                            double pesoUpdate = sc.nextDouble();

                            String updatePeso = "UPDATE manifestos_carga SET peso_total_carregado_kg = ? WHERE id = ?";

                            PreparedStatement psPesoUpdate = conexao.prepareStatement(updatePeso);
                            psPesoUpdate.setDouble(1, pesoUpdate);
                            psPesoUpdate.setInt(2, idUpdatePeso);
                            psPesoUpdate.executeUpdate();
                            System.out.println("Peso atualizado com sucesso!");
                            break;
                        case 4:
                            System.out.print("Digite o ID da Carga que deseja atualizar: ");
                            int idUpdateVeiculo = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Veículo: ");
                            int veiculoUpdate = sc.nextInt();

                            String updateVeiculo = "UPDATE manifestos_carga SET veiculo_id = ? WHERE id = ?";

                            PreparedStatement psVeiculoUpdate = conexao.prepareStatement(updateVeiculo);
                            psVeiculoUpdate.setInt(1, veiculoUpdate);
                            psVeiculoUpdate.setInt(2, idUpdateVeiculo);
                            psVeiculoUpdate.executeUpdate();
                            System.out.println("Veículo atualizado com sucesso!");
                            break;
                        case 5:
                            System.out.print("Digite o ID da Carga que deseja atualizar: ");
                            int idUpdateMotorista = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Motorista: ");
                            int motoristaUpdate = sc.nextInt();

                            String updateMotorista = "UPDATE manifestos_carga SET motorista_id = ? WHERE id = ?";

                            PreparedStatement psMotoristaUpdate = conexao.prepareStatement(updateMotorista);
                            psMotoristaUpdate.setInt(1, motoristaUpdate);
                            psMotoristaUpdate.setInt(2, idUpdateMotorista);
                            psMotoristaUpdate.executeUpdate();
                            System.out.println("Motorista atualizado com sucesso!");
                            break;
                        case 6:
                            System.out.print("Digite o ID da Carga que deseja atualizar: ");
                            int idUpdatePedido = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Pedido: ");
                            double pedidoUpdate = sc.nextDouble();

                            String updatePedido = "UPDATE manifestos_carga SET pedido_id = ? WHERE id = ?";

                            PreparedStatement psPedidoUpdate = conexao.prepareStatement(updatePedido);
                            psPedidoUpdate.setDouble(1, pedidoUpdate);
                            psPedidoUpdate.setInt(2, idUpdatePedido);
                            psPedidoUpdate.executeUpdate();
                            System.out.println("Pedido atualizado com sucesso!");
                            break;
                        case 7:
                            System.out.print("ID da Carga que deseja atualizar: ");
                            int idUpdate = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova data: ");
                            String dataTextoUpd2 = sc.nextLine();
                            DateTimeFormatter formatoBrUpd2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate dataUpd2 = LocalDate.parse(dataTextoUpd, formatoBrUpd);
                            System.out.print("Digite um novo Volume: ");
                            double volumeUpdate2 = sc.nextDouble();
                            System.out.print("Digite um novo Peso: ");
                            double pesoUpdate2 = sc.nextDouble();
                            System.out.print("Digite um novo Veículo: ");
                            int veiculoUpdate2 = sc.nextInt();
                            System.out.print("Digite um novo Motorista: ");
                            int motoristaUpdate2 = sc.nextInt();
                            System.out.print("Digite um novo Pedido: ");
                            int pedidoUpdate2 = sc.nextInt();

                            String update = "UPDATE manifestos_carga SET data_partida = ?, volume_total_ocupado = ?, peso_total_carregado_kg = ?, veiculo_id = ?, motorista_id = ?, pedido_id = ?wwwwww Where id = ?";

                            PreparedStatement psUpdate = conexao.prepareStatement(update);
                            psDataUpdate.setDate(1, java.sql.Date.valueOf(dataUpd2));
                            psUpdate.setDouble(2, volumeUpdate2);
                            psUpdate.setDouble(3, pesoUpdate2);
                            psUpdate.setDouble(4, veiculoUpdate2);
                            psUpdate.setInt(5, motoristaUpdate2);
                            psUpdate.setInt(6, pedidoUpdate2);
                            psUpdate.setInt(7, idUpdate);
                            psUpdate.executeUpdate();
                            System.out.println("Carga Atualizada com sucesso!");
                            break;
                        default:
                            System.out.println("Opção Inválida!");

                    }
                    break;
                case 4:
                    System.out.print("Selecione um ID para deletar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();

                    String delete = "DELETE FROM manifestos_carga WHERE id = ?";

                    PreparedStatement psDelete = conexao.prepareStatement(delete);
                    psDelete.setInt(1, idDelete);
                    psDelete.executeUpdate();
                    System.out.println();
                    System.out.println("Carga deletada!");
                    break;
                case 0:
                    System.out.println("\nGerenciamento de cargas concluído!");
                    break;
                default:
                    System.out.println("Selecione uma opção válida!");
            }

        } while (opcaoCarga != 0);



    }
}