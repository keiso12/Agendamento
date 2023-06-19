
//===========================================
//              AGENDAMENTO
//===========================================
package com.example;

import java.time.LocalDateTime;

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
