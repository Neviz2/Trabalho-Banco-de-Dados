
package br.com.agenciaviagens.view;

import br.com.agenciaviagens.model.Reserva; 
import br.com.agenciaviagens.repository.ReservaRepositorio;

import java.util.List;



public class Main {

    public static void main(String[] args) {
        
        // 1. Cria uma instância da CLASSE DE IMPLEMENTAÇÃO
        ReservaRepositorio meuRepositorio = new ReservaRepositorio();
        
        
        // 2. Chama o método e GUARDA o resultado em uma variável
        //List<Reserva> todasAsReservas = meuRepositorio.buscarTodas();
        

        List<Reserva> buscarCliente = meuRepositorio.buscarCliente("Pedro Gomes");
        
        System.out.println("\n--- Buscando todas as reservas no banco de dados ---");
        System.out.println("CLIENTE: " + buscarCliente);

       // for (Reserva reserva : todasAsReservas) {
       //     System.out.println(reserva);
      //  }
    }
}
