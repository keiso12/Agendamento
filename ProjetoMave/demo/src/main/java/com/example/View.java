package com.example;
import java.time.LocalDateTime;
import java.util.Scanner;

public class View {
    
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
}
