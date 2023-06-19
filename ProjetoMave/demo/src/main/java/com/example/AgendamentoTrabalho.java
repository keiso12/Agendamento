package com.example;
import java.time.LocalDateTime;

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
