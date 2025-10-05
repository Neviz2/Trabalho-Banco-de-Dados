package br.com.agenciaviagens.model;

import java.time.LocalDate;

public class Reserva {
   
    private String cliente;
    private String pacote;
    private double valor;
    private LocalDate dataSaida;
    private String status;

    public Reserva(String cliente, String pacote, double valor, LocalDate dataSaida, String status) {
        this.cliente = cliente;
        this.pacote = pacote;
        this.valor = valor;
        this.dataSaida = dataSaida;
        this.status = status;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPacote() {
        return pacote;
    }

    public void setPacote(String pacote) {
        this.pacote = pacote;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 3. Um método toString() para facilitar a impressão do objeto
    @Override
    public String toString() {
        return "Reserva [cliente=" + cliente + ", pacote=" + pacote + ", valor=" + valor + ", dataSaida=" + dataSaida
                + ", status=" + status + "]";
    }
} 
