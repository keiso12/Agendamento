package com.example;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

//===========================================
//                  MENU
//===========================================
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Scanner scanner = new Scanner(System.in);
            int opcao;
    
            do {
                View.exibirMenu();
                System.out.print("Digite o número da opção desejada: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o scanner
    
                switch (opcao) {
                    case 1:
                        View.adicionarAgendamentoPessoal();
                        break;
                    case 2:
                        View.adicionarAgendamentoTrabalho();
                        break;
                    case 3:
                        View.listarAgendamentos();
                        break;
                    case 4:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");

                }

                System.out.println();

            }while (opcao != 4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//===========================================
//              BANCO DE DADOS
//===========================================

class DBAgendamento {
    
    static String url = "jdbc:mysql://localhost:3306/Agendamento";
    static Connection connection;
    
    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, "root", "positivo");
        }
        return connection;
    }

    public static void inserirAgendamento(Agendamento agendamento) throws SQLException {
        String sql = "INSERT INTO agendamentos (data_agendamento, descricao, tipo_agendamento, prioridade) VALUES (?,?,?,?)";
        
        try (Connection connection = getConnection();
            PreparedStatement prst = connection.prepareStatement(sql)){
                prst.setObject(1, agendamento.getDataAgendamento());
                prst.setString(2, agendamento.getDescricao());
                prst.setObject(3, agendamento.getTipo().name());
                prst.setString(4, agendamento.getPrioridade().name());
                
                prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ListarAgendamentos(){
        String sql = "SELECT * FROM agendamentos";

        try (Connection connection = getConnection();
            PreparedStatement prst = connection.prepareStatement(sql)) {
            
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                
                int id = rs.getInt("id");
                LocalDateTime data = rs.getObject("data_agendamento", LocalDateTime.class);
                String prioridade = rs.getString("prioridade");

                System.out.println("Senha: "+ id + "\nData: " + data + "\nPrioridade: " + prioridade + "\n--------------------------\n");
            }

        }
         catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static Agendamento buscarAgendamento(int id) throws SQLException {
        String sql = "SELECT * FROM agendamentos WHERE id = ?";

        try (Connection connection = getConnection();
            PreparedStatement prst = connection.prepareStatement(sql)) {
            prst.setInt(1, id);

            try (ResultSet resultSet = prst.executeQuery()) {
                
                if (resultSet.next()) {
                    LocalDateTime dataAgendamento = resultSet.getObject("data_agendamento", LocalDateTime.class);
                    String descricao = resultSet.getString("descricao");
                    Agendamento.TipoAgendamento tipo = Agendamento.TipoAgendamento.valueOf(resultSet.getString("tipo_agendamento"));
                    Agendamento.Prioridade prioridade = Agendamento.Prioridade.valueOf(resultSet.getString("prioridade"));

                    return new Agendamento(dataAgendamento, descricao, tipo, prioridade);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    return null;
}


    public static void atualizarAgendamento(Agendamento agendamento) throws SQLException {
        String sql="UPDATE agendamentos SET data_agendamento = ?, descricao = ?, tipo_agendamento = ?, prioridade = ? WHERE id = ?";

        try (Connection connection = getConnection();
            PreparedStatement prst = connection.prepareStatement(sql)){
            prst.setObject(1, agendamento.getDataAgendamento());
            prst.setString(2, agendamento.getDescricao());
            prst.setObject(3, agendamento.getTipo());
            prst.setObject(4, agendamento.getPrioridade());

            prst.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void excluirAgendamento(int id) throws SQLException {
        String sql = "DELETE FROM agendamentos WHERE id = ?";

        try (Connection connection = getConnection();
            PreparedStatement prst = connection.prepareStatement(sql)){
            prst.setInt(1, id);

            prst.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
