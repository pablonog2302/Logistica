import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Veiculo {
    public void executarVeiculo(Connection conexao) throws Exception{
        Scanner sc = new Scanner(System.in);

        int opcaoV;

        do {
            System.out.println("""
                        
                        ====MENU DO VEÍCULO======
                        1| Cadastrar Veículo
                        2| Listar Veículos
                        3| Atualizar dados do Veículo
                        4| Deletar Veículo
                        0| Voltar
                        
                        Escolha uma opção: 
                        """);
            opcaoV = sc.nextInt();
            sc.nextLine();

            switch (opcaoV){
                case 1:

                    System.out.println("\n===Cadastrar Veículo===");

                    System.out.print("Digite a placa do Veículo: ");
                    String placa = sc.nextLine();
                    System.out.print("Digite o modelo do Veículo: ");
                    String modelo = sc.nextLine();
                    System.out.print("Digite a capacidade de peso do Veículo: ");
                    double peso = sc.nextDouble();
                    System.out.print("Digite a largura interna do Veículo: ");
                    double largura = sc.nextDouble();
                    System.out.print("Digite a altura interna do Veículo: ");
                    double altura = sc.nextDouble();
                    System.out.print("Digite o comprimento interno interna do Veículo: ");
                    double comprimento = sc.nextDouble();
                    System.out.print("Digite o volume máximo do Veículo: ");
                    double volumeMax = sc.nextDouble();

                    String insert = "INSERT INTO veiculo (placa, modelo, capacidade_peso_kg, largura_interna, altura_interna, comprimento_interno,volume_max_m3) VALUES (?,?,?,?,?,?,?)";

                    PreparedStatement psInsert = conexao.prepareStatement(insert);
                    psInsert.setString(1, placa);
                    psInsert.setString(2, modelo);
                    psInsert.setDouble(3, peso);
                    psInsert.setDouble(4, largura);
                    psInsert.setDouble(5, altura);
                    psInsert.setDouble(6, comprimento);
                    psInsert.setDouble(7, volumeMax);
                    psInsert.executeUpdate();
                    System.out.println("Veículo cadastrado com sucesso!");
                    break;
                case 2:
                    String select = "SELECT * FROM veiculo";
                    Statement stat = conexao.createStatement();
                    ResultSet rs = stat.executeQuery(select);

                    System.out.print("""
                                =====Veículos====
                                ID | Placa | Modelo | Capacidade de Peso | Largura Interna | Altura Interna | Comprimento Interno | Volume Máximo (m³)
                                """);
                    while(rs.next()) {
                        System.out.printf("%d - %s - %s - %.2fkg - %.2fm - %.2fm - %.2fm - %.2fm³%n",
                                rs.getInt("id"),
                                rs.getString("placa"),
                                rs.getString("modelo"),
                                rs.getDouble("capacidade_peso_kg"),
                                rs.getDouble("largura_interna"),
                                rs.getDouble("altura_interna"),
                                rs.getDouble("comprimento_interno"),
                                rs.getDouble("volume_max_m3"));
                    }
                    break;
                case 3:
                    int opcaoUpd;

                    System.out.print("""
                                =====ATUALIZAÇÃO DE DADOS=====
                                - O que você deseja atualizar?
                                1| Placa
                                2| Modelo
                                3| Capacidade de peso
                                4| Largura Interna
                                5| Altura Interna
                                6| Comprimento Interno
                                7| Volume Máximo
                                8| Todos os dados
                                
                                """);
                    opcaoUpd = sc.nextInt();
                    sc.nextLine();

                    switch (opcaoUpd){
                        case 1:
                            System.out.print("Digite o ID do Veículo que deseja atualizar: ");
                            int idUpdatePlaca = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova placa: ");
                            String placaUpdate = sc.nextLine();

                            String updatePlaca = "UPDATE veiculo SET placa = ? WHERE id = ?";

                            PreparedStatement psPlacaUpdate = conexao.prepareStatement(updatePlaca);
                            psPlacaUpdate.setString(1, placaUpdate);
                            psPlacaUpdate.setInt(2, idUpdatePlaca);
                            psPlacaUpdate.executeUpdate();
                            System.out.println("Placa Atualizada com sucesso!");
                            break;
                        case 2:
                            System.out.print("Digite o ID do Veículo que deseja atualizar: ");
                            int idUpdateModelo = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo Modelo: ");
                            String modeloUpdate = sc.nextLine();

                            String updateModelo = "UPDATE veiculo SET modelo = ? WHERE id = ?";

                            PreparedStatement psModeloUpdate = conexao.prepareStatement(updateModelo);
                            psModeloUpdate.setString(1, modeloUpdate);
                            psModeloUpdate.setInt(2, idUpdateModelo);
                            psModeloUpdate.executeUpdate();
                            System.out.println("Modelo Atualizado com sucesso!");
                            break;
                        case 3:
                            System.out.print("Digite o ID do Veículo que deseja atualizar: ");
                            int idUpdatePeso = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova capacidade de peso: ");
                            double pesoUpdate = sc.nextDouble();

                            String updatePeso = "UPDATE veiculo SET capacidade_peso_kg = ? WHERE id = ?";

                            PreparedStatement psEmailUpdate = conexao.prepareStatement(updatePeso);
                            psEmailUpdate.setDouble(1, pesoUpdate);
                            psEmailUpdate.setInt(2, idUpdatePeso);
                            psEmailUpdate.executeUpdate();
                            System.out.println("Capacidade de Peso atualizada com sucesso!");
                            break;
                        case 4:
                            System.out.print("Digite o ID do Veículo que deseja atualizar: ");
                            int idUpdateLargura = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova largura: ");
                            double larguraUpdate = sc.nextDouble();

                            String updatelargura = "UPDATE veiculo SET largura_interna = ? WHERE id = ?";

                            PreparedStatement psLarguraUpdate = conexao.prepareStatement(updatelargura);
                            psLarguraUpdate.setDouble(1, larguraUpdate);
                            psLarguraUpdate.setInt(2, idUpdateLargura);
                            psLarguraUpdate.executeUpdate();
                            System.out.println("Largura Atualizada com sucesso!");
                            break;
                        case 5:
                            System.out.print("Digite o ID do Veículo que deseja atualizar: ");
                            int idUpdateAltura = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova Altura: ");
                            double alturaUpdate = sc.nextDouble();

                            String updateAltura = "UPDATE veiculo SET altura_interna = ? WHERE id = ?";

                            PreparedStatement psAlturaUpdate = conexao.prepareStatement(updateAltura);
                            psAlturaUpdate.setDouble(1, alturaUpdate);
                            psAlturaUpdate.setInt(2, idUpdateAltura);
                            psAlturaUpdate.executeUpdate();
                            System.out.println("Altura Atualizada com sucesso!");
                            break;
                        case 6:
                            System.out.print("Digite o ID do Veículo que deseja atualizar: ");
                            int idUpdateComprimento = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo comprimento: ");
                            double comprimentoUpdate = sc.nextDouble();

                            String updateComprimento = "UPDATE veiculo SET comprimento_interno = ? WHERE id = ?";

                            PreparedStatement psComprimentoUpdate = conexao.prepareStatement(updateComprimento);
                            psComprimentoUpdate.setDouble(1, comprimentoUpdate);
                            psComprimentoUpdate.setInt(2, idUpdateComprimento);
                            psComprimentoUpdate.executeUpdate();
                            System.out.println("Comprimento atualizado com sucesso!");
                            break;
                        case 7:
                            System.out.print("Digite o ID do Veículo que deseja atualizar: ");
                            int idUpdateVolume = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite um novo volume máximo: ");
                            double volumeMaxUpdate = sc.nextDouble();

                            String updateVolumeMax = "UPDATE veiculo SET volume_max_m3 = ? WHERE id = ?";

                            PreparedStatement psVolumeUpdate = conexao.prepareStatement(updateVolumeMax);
                            psVolumeUpdate.setDouble(1, volumeMaxUpdate);
                            psVolumeUpdate.setInt(2, idUpdateVolume);
                            psVolumeUpdate.executeUpdate();
                            System.out.println("Volume Máximo atualizado com sucesso!");
                            break;
                        case 8:
                            System.out.print("ID do Veículo que deseja atualizar: ");
                            int idUpdate = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite uma nova placa: ");
                            String placaUpdate2 = sc.nextLine();
                            System.out.print("Digite um novo Modelo: ");
                            String modeloUpdate2 = sc.nextLine();
                            System.out.print("Digite uma nova capacidade de peso: ");
                            double pesoUpdate2 = sc.nextDouble();
                            System.out.print("Digite uma nova largura: ");
                            double larguraUpdate2 = sc.nextDouble();
                            System.out.print("Digite uma nova Altura: ");
                            double alturaUpdate2 = sc.nextDouble();
                            System.out.print("Digite um novo comprimento: ");
                            double comprimentoUpdate2 = sc.nextDouble();
                            System.out.print("Digite um novo volume máximo: ");
                            double volumeMaxUpdate2 = sc.nextDouble();

                            String update = "UPDATE veiculo SET placa = ?, modelo = ?, capacidade_peso_kg = ?, largura_interna = ?, altura_interna = ?, comprimento_interno = ?, volume_max_m3 = ? Where id = ?";

                            PreparedStatement psUpdate = conexao.prepareStatement(update);
                            psUpdate.setString(1, placaUpdate2);
                            psUpdate.setString(2, modeloUpdate2);
                            psUpdate.setDouble(3, pesoUpdate2);
                            psUpdate.setDouble(4, larguraUpdate2);
                            psUpdate.setDouble(5, alturaUpdate2);
                            psUpdate.setDouble(6, comprimentoUpdate2);
                            psUpdate.setDouble(7, volumeMaxUpdate2);
                            psUpdate.setInt(8, idUpdate);
                            psUpdate.executeUpdate();
                            System.out.println("Veículo Atualizado com sucesso!");
                            break;
                        default:
                            System.out.println("Opção Inválida!");

                    }
                    break;
                case 4:
                    System.out.print("Selecione um ID para deletar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();

                    String delete = "DELETE FROM veiculo WHERE id = ?";

                    PreparedStatement psDelete = conexao.prepareStatement(delete);
                    psDelete.setInt(1, idDelete);
                    psDelete.executeUpdate();
                    System.out.println();
                    System.out.println("Veículo deletado!");
                    break;
                case 0:
                    System.out.println("\nGerenciamento de veículos concluído!");
                    break;
                default:
                    System.out.println("Selecione uma opção válida!");
            }

        } while (opcaoV != 0);



    }
}