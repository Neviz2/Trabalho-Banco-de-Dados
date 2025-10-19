package br.com.agenciaviagens.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bson.Document;

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

public static Reserva fromDocument(Document doc) {
    String cliente = doc.getString("cliente");
    String pacote = doc.getString("pacote");

    // Solução segura para qualquer tipo numérico (Integer ou Double)
    Number valorNumber = doc.get("valor", Number.class);
    double valor = valorNumber != null ? valorNumber.doubleValue() : 0.0;

    // Trata a data como string — mas protege contra null
    String dataSaidaStr = doc.getString("dataSaida");
    LocalDate dataSaida = (dataSaidaStr != null && !dataSaidaStr.isEmpty())
        ? LocalDate.parse(dataSaidaStr)
        : null;

    String status = doc.getString("status");

    return new Reserva(cliente, pacote, valor, dataSaida, status);
}

    
    // @Override
    // public String toString() {
    //     return "Reserva [cliente=" + cliente + ", pacote=" + pacote + ", valor=" + valor + ", dataSaida=" + dataSaida
    //             + ", status=" + status + "]";
    // }

    @Override
public String toString() {
    // Formatador para data no padrão BR


    // Usa String.format para alinhar e formatar o valor como moeda
    return String.format(
        "----------------------------------------\n" +
        " Cliente:    %s\n" +
        " Pacote:     %s\n" +
        " Status:     %s\n" +
        " Data Saída: %s | Valor: R$ %.2f\n" +
        "----------------------------------------",
        this.cliente,
        this.pacote,
        this.status,
        this.dataSaida,
        this.valor
    );
}
} 
