
package br.com.agenciaviagens.repository;

// imports do java:
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

// imports do MongoDB:
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// import com.mongodb.client.model.Filters; se precisar de filtros mias complexos depois.

import br.com.agenciaviagens.model.Reserva;

import org.bson.Document;
import org.bson.conversions.Bson;

public class ReservaRepositorio{

    
    private MongoCollection<Document> collection;

    
    public ReservaRepositorio() {
       
        String connectionString = "mongodb+srv://DbLucasNeves:33328480@cluster0.owjzzcg.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("agencia_viagens");
        this.collection = database.getCollection("reservas");

        System.out.println("Conexão com o MongoDB estabelecida com sucesso!");
    }

    
public List<Reserva> buscarTodas() {
    List<Reserva> reservas = new ArrayList<>();

    String consultaJson = "{}";
    Document filtro = Document.parse(consultaJson);

    for (Document doc : collection.find(filtro)) {

        
        
        Reserva reserva = new Reserva(consultaJson, consultaJson, 0, null, consultaJson);
        
        reserva.setCliente(doc.getString("cliente"));
        reserva.setPacote(doc.getString("pacote"));
        reserva.setStatus(doc.getString("status"));
      
        Number valorDoBanco = doc.get("valor", Number.class); 
        if (valorDoBanco != null) {
            reserva.setValor(valorDoBanco.doubleValue()); 
        } else {
            reserva.setValor(0.0);
        }     
        
        String dataString = doc.getString("data_saida");
        if (dataString != null && !dataString.isEmpty()) {
            reserva.setDataSaida(LocalDate.parse(dataString));
        }
        
        reservas.add(reserva);
    }

    System.out.println(reservas.size() + " reservas encontradas no banco de dados.");
    return reservas;
}


    public List<Reserva> buscarCliente(String nomeCli){
    List<Reserva> reservas = new ArrayList<>();

    Bson filtro = Filters.regex("cliente", "^" + nomeCli + "$", "i");

    for(Document doc : collection.find(filtro)){
        Reserva reserva = Reserva.fromDocument(doc);
        reservas.add(reserva);
    }

    return reservas;
}

    // Aqui virão os outros métodos: buscarTodas(), salvar(), etc.
}