
//===========================================
//           AGENDAMENTO PESSOAL
//===========================================
package com.example;

import java.time.LocalDateTime;

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

