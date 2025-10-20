
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
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

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

    public boolean deletarCliente(String nomeCli) {
    long deletados = collection.deleteOne(new Document("cliente", nomeCli)).getDeletedCount();

    if(deletados > 0){
        System.out.println("Reserva de " + nomeCli + " foi apagada com sucesso!");
        return true;
    }else{
        System.out.println("Reserva nao encontrada!");
        return false;
    }
}
//
    public void substituirRseserva(String nomeCli, Reserva novaReserva) {

        Document filtro = new Document("cliente", nomeCli);
        Document novoDoc = new Document()
                .append("cliente", novaReserva.getCliente())
                .append("pacote", novaReserva.getPacote())
                .append("valor", novaReserva.getValor())
                .append("data_Saida", novaReserva.getDataSaida().toString())
                .append("status", novaReserva.getStatus());

        collection.replaceOne(filtro, novoDoc);
        System.out.println("Reserva de " + nomeCli + " foi atualizada com sucesso!");
    }
//
    public void calcular(String nomePacote){
    double somaValores = 0.0;
    Document filtro = new Document("pacote", nomePacote);
    for (Document doc : collection.find(filtro)) {
        Reserva reserva = Reserva.fromDocument(doc);
        if (nomePacote.equals(reserva.getPacote())) {
            somaValores += reserva.getValor(); // Paris 5 dias
        }
        
    }
    System.out.println("Soma dos valores das reservas do pacote " + nomePacote + ": " + somaValores);
    
}
    public void inserirReserva(Reserva novaReserva) {
        // converte o objeto para bson
        Document doc = new Document()
                .append("_id", new ObjectId()) // gera um id
                .append("cliente", novaReserva.getCliente())
                .append("pacote", novaReserva.getPacote())
                .append("valor", novaReserva.getValor())
                .append("data_saida", novaReserva.getDataSaida().toString()) 
                .append("status", novaReserva.getStatus());

        try {
            // Insere o documento na coleção
            collection.insertOne(doc);
            System.out.println("Reserva inserida com sucesso! ID: ");
        } catch (Exception e) {
            System.err.println("Erro ao inserir reserva: " + e.getMessage());
        }
    }

    
    public boolean alterarDataSaida(String nomeCli, String novaData) {
        
        Bson filtro = Filters.regex("cliente", "^" + nomeCli + "$", "i");
        Bson atualizacao = Updates.set("data_saida", novaData);

        try {
            UpdateResult resultado = collection.updateOne(filtro, atualizacao);

            if (resultado.getModifiedCount() > 0) {
                System.out.println("Data de saída da reserva de " + nomeCli + " atualizada com sucesso.");
                return true;
            } else {
                System.out.println("Nenhuma reserva encontrada para o cliente: " + nomeCli + ". Nada foi alterado.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar data da reserva: " + e.getMessage());
            return false;
        }
    }

}
