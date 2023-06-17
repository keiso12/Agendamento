import java.time.LocalDateTime;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {

    public static void exibirMenu() {
        System.out.println("Menu:");
        System.out.println("1. Adicionar Agendamento Pessoal");
        System.out.println("2. Adicionar Agendamento de Trabalho");
        System.out.println("3. Listar Agendamentos");
        System.out.println("4. Sair");
    }

    public static void adicionarAgendamentoPessoal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Data de Agendamento (AAAA-MM-DD HH:MM):");
        LocalDateTime dataAgendamento = LocalDateTime.parse(scanner.nextLine());
        System.out.println("Descrição:");
        String descricao = scanner.nextLine();
        System.out.println("Nome:");
        String nome = scanner.nextLine();
        System.out.println("Prioridade (ALTA ou BAIXA):");
        String prioridade = scanner.nextLine();

        AgendamentoPessoal agendamentoPessoal = new AgendamentoPessoal(dataAgendamento, descricao, nome, Agendamento.TipoAgendamento.PESSOAL, Agendamento.Prioridade.valueOf(prioridade));

        try {
            DBAgendamento.inserirAgendamento(agendamentoPessoal);
            System.out.println("Agendamento Pessoal adicionado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void adicionarAgendamentoTrabalho() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Data de Agendamento (AAAA-MM-DD HH:MM):");
        LocalDateTime dataAgendamento = LocalDateTime.parse(scanner.nextLine());
        System.out.println("Descrição:");
        String descricao = scanner.nextLine();
        System.out.println("Empresa:");
        String empresa = scanner.nextLine();
        System.out.println("Prioridade (ALTA ou BAIXA):");
        String prioridade = scanner.nextLine();

        AgendamentoTrabalho agendamentoTrabalho = new AgendamentoTrabalho(dataAgendamento, descricao, Agendamento.TipoAgendamento.PROFISSIONAL, Agendamento.Prioridade.valueOf(prioridade), empresa);

        try {
            DBAgendamento.inserirAgendamento(agendamentoTrabalho);
            System.out.println("Agendamento de Trabalho adicionado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listarAgendamentos() {
        System.out.println("Lista de Agendamentos:");
        DBAgendamento.ListarAgendamentos();
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Scanner scanner = new Scanner(System.in);
            int opcao;
    
            do {
                exibirMenu();
                System.out.print("Digite o número da opção desejada: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o scanner
    
                switch (opcao) {
                    case 1:
                        adicionarAgendamentoPessoal();
                        break;
                    case 2:
                        adicionarAgendamentoTrabalho();
                        break;
                    case 3:
                        listarAgendamentos();
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
class Agendamento {

    private LocalDateTime dataAgendamento;
    private String descricao;
    private TipoAgendamento tipo;
    private Prioridade prioridade;

    // CONSTRUTOR
    public Agendamento( LocalDateTime dataAgendamento, String descricao, TipoAgendamento tipo, Prioridade prioridade) {
        this.dataAgendamento = dataAgendamento;
        this.descricao = descricao;
        this.tipo = tipo;
        this.prioridade = prioridade;
    }

    // GETTER E SETTER
    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }
    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoAgendamento getTipo() {
        return tipo;
    }
    public void setTipo(TipoAgendamento tipo) {
        this.tipo = tipo;
    }
    
    public Prioridade getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    // ENUM
    public enum TipoAgendamento {
        PESSOAL,
        PROFISSIONAL,
        OUTRO
    }

    public enum Prioridade {
        ALTA,
        BAIXA
    }

}
class DBAgendamento {
    
    static String url = "jdbc:mysql://localhost:3306/Agendamento";
    static Connection connection;
    
    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, "root", "kenzokoga1");
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

class AgendamentoPessoal extends Agendamento {
    private String nome;

    // CONSTRUTOR
    public AgendamentoPessoal(LocalDateTime dataAgendamento, String descricao, String nome, TipoAgendamento tipo, Prioridade prioridade) {
        super(dataAgendamento, descricao, tipo, prioridade);
        this.nome = nome;
    }

    // GETTER E SETTERS
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

class AgendamentoTrabalho extends Agendamento {
    private String empresa;

    // CONSTRUTOR
    public AgendamentoTrabalho(LocalDateTime dataAgendamento, String descricao, TipoAgendamento tipo, Prioridade prioridade, String empresa) {
        super(dataAgendamento, descricao, tipo, prioridade);
        this.empresa = empresa;
    }

    // GETTERS E SETTERS
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
