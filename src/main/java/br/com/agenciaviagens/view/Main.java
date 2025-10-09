
package br.com.agenciaviagens.view;

import br.com.agenciaviagens.model.Reserva; 
import br.com.agenciaviagens.repository.ReservaRepositorio;

import java.util.List;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
        
         Scanner tec = new Scanner(System.in);
        String nomeCliente, pacote, status, data;
        double valor = 0;

        ReservaRepositorio meuRepositorio = new ReservaRepositorio();

        int opcao = 0; 

        do{
            System.out.println("\n--- Menu de Opções ---");
            System.out.println("1. Buscar todas as reservas");
            System.out.println("2. Buscar reservas por cliente");
            System.out.println("3. Deletar reserva por cliente");
            System.out.println("4. Atualizar reserva por cliente");
            System.out.println("5.")
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = tec.nextInt();
            tec.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    // Lógica para buscar todas as reservas
                    System.out.println("Buscando todas as reservas...");
                    break;
                case 2:
                    // Lógica para buscar reservas por cliente
                    System.out.print("Digite o nome do cliente: ");
                     nomeCliente = tec.nextLine();
                    System.out.println("Buscando reservas para o cliente: " + nomeCliente);
                    List<Reserva> buscarCliente = meuRepositorio.buscarCliente(nomeCliente);
                    for (Reserva reserva : buscarCliente) {
                        System.out.println(reserva);
                    }
                    break;
                case 3:
                    System.out.println("Informe o nome do cliente: ");
                    nomeCliente = tec.nextLine();
                    boolean certo = meuRepositorio.deletarCliente(nomeCliente);

                    if(certo){
                        System.out.println("Reserva deletada com sucesso!");
                    } else {
                        System.out.println("Nenhuma reserva encontrada para o cliente informado!");
                    }
                    break;
                case 4:
                    System.out.println("Digite o nome do cliente para atualizar a reserva: ");
                    nomeCliente = tec.nextLine();
                    System.out.println("Digite o novo pacote: ");
                    pacote = tec.nextLine();
                    System.out.println("Digite o novo valor: ");
                    valor = tec.nextDouble();
                    tec.nextLine(); // Consumir a quebra de linha
                    System.out.println("Digite a nova data de saída (YYYY-MM-DD): ");
                    data = tec.nextLine();
                    System.out.println("Digite o novo status: ");
                    status = tec.nextLine();
                    Reserva novaReserva = new Reserva(nomeCliente, pacote, valor, java.time.LocalDate.parse(data), status);
                    meuRepositorio.substituirRseserva(nomeCliente, novaReserva);
                    System.out.println("Reserva atualizada com sucesso!");
                    break;
                case 5:
                    System.out.println("Saindo...");               
                    break;
                case 6:
                    System.out.println("Saindo...");      
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }


        }
        while (opcao != 5);

        
    
        


        tec.close();
        
    }
}
