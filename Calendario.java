import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.lang.model.util.ElementScanner14;

public class Calendario {

    Connection connect;
    PreparedStatement ps;
    
    
    public void conectar_bd() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Agendamento";

            connect =  DriverManager.getConnection(url, "root", "kenzokoga1");

            // System.out.println("Conectado ao banco de dados.");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void mostrar_tabelas() {
        try {
            // Comando no sql
            String sql = "SHOW TABLES";

            // Envia pro BD
            ps = connect.prepareStatement(sql);
            ps.execute();
            
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println(tableName);
            }
    
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void criar_tabela(){
        try {
            String sql = "CREATE TABLE Agendar(horario varchar(10),dia int,mes varchar(10), primary key(horario));";
            ps = connect.prepareStatement(sql);
            ps.execute();

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    public void adicionar_horario(String horario, int dia, int mes) {
        if (mes == 1 && dia > 31 || mes == 3 && dia > 31 || mes == 5 && dia > 31 || mes == 7 && dia > 31 || mes == 9 && dia > 31 || mes == 11 && dia > 31) {
            System.out.println("DIA INVÁLIDO!");
        } else if (mes == 2 && dia > 28) {
            System.out.println("DIA INVÁLIDO!");
        } else if (mes == 4  && dia > 30|| mes == 6  && dia > 30|| mes == 8  && dia > 30|| mes == 10  && dia > 30|| mes == 12 && dia > 30) {
            System.out.println("DIA INVÁLIDO!");
        } else {
            try {
                String sql = "INSERT INTO agendar (horario, dia, mes) VALUES (?,?,?)";
                ps = connect.prepareStatement(sql);

                ps.setString(1, horario);
                ps.setInt(2, dia);
                ps.setInt(3, mes);

                ps.execute();

            } catch (Exception e) {
                
                try {
                    throw new Exception("Horário desta data já está ocupada!");
                } catch (Exception e1) {
                    
                    e1.printStackTrace();
                }
            }
        }
    }

    public void listar_agenda() {
        try {

            // Comando no sql
            String sql = "SELECT * FROM agendar";
            
            ps = connect.prepareStatement(sql);

            // Executando a query
            boolean tem_retorno = ps.execute();

            if (tem_retorno) {
                // Pegando resultados
                ResultSet rs = ps.getResultSet();

                while (rs.next()) {
                    // Cada um dos resultados
                    String horario = rs.getString("horario");
                    int dia = rs.getInt("dia");
                    int mes = rs.getInt("mes");

                    System.out.println(horario +" | " + dia +" | " + mes);
                    
                }

            }
    
            // ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remover_horario(String horario, int dia, int mes) {
        try {

            String sql = "DELETE FROM agendar WHERE horario = ? and dia = ? and mes = ?";
            ps = connect.prepareStatement(sql);

            ps.setString(1, horario);
            ps.setInt(2, dia);
            ps.setInt(3, mes);

            ps.execute();

            System.out.println("Horário removido com sucesso!");
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Calendario calendario = new Calendario();

        calendario.conectar_bd();
        // calendario.mostrar_tabelas();
        // calendario.criar_tabela();
        calendario.adicionar_horario("12:00", 22, 4);
        calendario.listar_agenda();
        // calendario.remover_horario("12:00", 22, 2);
    }
}
