package com.sistemaoficina.dados;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sistemaoficina.dto.Cliente;
import com.sistemaoficina.dto.Veiculo;
import com.sistemaoficina.enums.Combustivel;

public class DadosVeiculo {

    private static final String ARQUIVO_VEICULOS = "bd/veiculo.json";

    public static ArrayList<Veiculo> listaVeiculos = carregarVeiculos();

    public static void salvarVeiculosJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_VEICULOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaVeiculos, writer);            
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public static ArrayList<Veiculo> carregarVeiculos() {
        try (FileReader reader = new FileReader(ARQUIVO_VEICULOS)) {
            Type listaTipo = new TypeToken<ArrayList<Veiculo>>(){}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de produtos não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    public static void cadastrar(Scanner scanner){
        System.out.println("Cadastrar veículo");
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo do veículo: ");
        String modelo = scanner.nextLine();
        System.out.print("Ano do veículo: ");
        Integer ano = Integer.parseInt(scanner.nextLine());
        System.out.print("Cor do veículo: ");
        String cor = scanner.nextLine();
        System.out.print("Combustível do veículo: ");
        Combustivel combustivel = selecionarCombustivel(scanner);

        Veiculo veiculo = new Veiculo(placa, modelo, ano, cor, combustivel);

        OptionalInt maxId = listaVeiculos.stream()
            .mapToInt(Veiculo::getId)
            .max();
        veiculo.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);

        listaVeiculos.add(veiculo);
        salvarVeiculosJson();
        
        System.out.println("Produto cadastrado com sucesso.");
    }

    public static Combustivel selecionarCombustivel(Scanner scanner) {
        System.out.println("Selecione o tipo de combustível do veiculo:");
        System.out.println("1 - Diesel");
        System.out.println("2 - Álcool");
        System.out.println("3 - Gasolina");
        System.out.println("4 - Flex");
        System.out.println("5 - Hibrido");
        System.out.println("6 - Elétrico");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        return switch (opcao) {
            case 1 -> Combustivel.DIESEL;
            case 2 -> Combustivel.ALCOOL;
            case 3 -> Combustivel.GASOLINA;
            case 5 -> Combustivel.FLEX;
            case 4 -> Combustivel.HIBRIDO;
            case 6 -> Combustivel.ELETRICO;
            default -> {
                System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
                yield selecionarCombustivel(scanner);
            }
        };
    }

    public static String getTextoCombustivel(Combustivel combustivel){
        if(combustivel == null){
            return "Combustível Indefinido";
        }
        return switch (combustivel) {
            case GASOLINA -> "Gasolina";
            case ALCOOL -> "Álcool";
            case DIESEL -> "Diesel";
            case FLEX -> "Flex";
            case ELETRICO -> "Elétrico";
            case HIBRIDO -> "Híbrido";
        };
    }


    public static void editar(Scanner scanner){
        System.out.println("Escolha um veiculo por Id:");
        listar();
        if(listaVeiculos.isEmpty()) return;
        int idVeiculo = Integer.parseInt(scanner.nextLine());
        Veiculo veiculo = buscarId(idVeiculo);
        if (veiculo == null) {
            System.out.println("Veículo não existente!");
            return;
        }   
        System.out.println("Digite as informações do Veículo");
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo do veículo: ");
        String modelo = scanner.nextLine();
        System.out.print("Ano do veículo: ");
        Integer ano = Integer.parseInt(scanner.nextLine());
        System.out.print("Cor do veículo: ");
        String cor = scanner.nextLine();
        System.out.print("Combustível do veículo: ");
        Combustivel combustivel = selecionarCombustivel(scanner);

        
        Veiculo novoVeiculo = new Veiculo(placa, modelo, ano, cor, combustivel);
        novoVeiculo.setId(veiculo.getId());

        int index = listaVeiculos.indexOf(veiculo);

        if (index != -1) {
            listaVeiculos.set(index, novoVeiculo);
        }

        salvarVeiculosJson();
        
        System.out.println("Veículo editado com sucesso.");
    }    

    public static void listar(){
        if(listaVeiculos.isEmpty()){
            System.out.println("Não há veículos para serem listados!");
        }
        for(Veiculo v : listaVeiculos){
            System.out.println("Id: " + v.getId() + " - " + v.getPlaca() + " - Modelo: " + v.getModelo());
        }
    }
    
    public static Veiculo buscarId(int id){
        for(Veiculo v : listaVeiculos){
            if(v.getId() == id){
                return v;
            }
        }
        return null;
    }
    
    public static void excluir(Scanner scanner) {
        listar();
        if(listaVeiculos.isEmpty()) return;
        System.out.print("Digite o id do veículo que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Veiculo v = buscarId(indice);
        if(v == null){
            System.out.println("Veículo não existente!");
            return;
        }

        for (Cliente cliente : DadosClientes.listaClientes) {
            if (cliente.getVeiculos() != null && cliente.getVeiculos().contains(v.getId())) {
                System.out.println("Este veículo está vinculado a um cliente. Desvincule-o antes de excluir.");
                return;
            }
        }

        listaVeiculos.remove(v);
        salvarVeiculosJson(); 
        System.out.println("Veículo excluido com sucesso.");
    }

    public static void atribuirCliente(Scanner scanner){
        listar();
        if(listaVeiculos.isEmpty()) return;
        System.out.print("Digite o ID do veículo a ser atribuido: ");
        int idVeiculo = Integer.parseInt(scanner.nextLine());
        Veiculo veiculo = buscarId(idVeiculo);
        
        if (veiculo == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        DadosClientes.listar();
        if(DadosClientes.listaClientes.isEmpty()) return;
        System.out.print("Digite o ID do cliente dono do veículo: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        Cliente cliente = DadosClientes.buscarId(idCliente);
        if(cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        int index = DadosClientes.listaClientes.indexOf(cliente);   

        ArrayList<Integer> veiculosCliente = cliente.getVeiculos();
        if (veiculosCliente == null) {
            veiculosCliente = new ArrayList<>();
        }

        if (veiculosCliente.contains(veiculo.getId())) {
            System.out.println("Este veículo já está atribuído ao cliente.");
            return;
        }
        for (Cliente c : DadosClientes.listaClientes) {
            if (c.getVeiculos().contains(veiculo.getId()) && c.getId() != cliente.getId()) {
                System.out.println("Este veículo já está atribuído a outro cliente.");
                return;
            }
        }

        veiculosCliente.add(veiculo.getId());        
        cliente.setVeiculos(veiculosCliente);
        if(index != -1) {
            DadosClientes.listaClientes.set(index, cliente);
        }
        DadosClientes.salvarClientesJson();      
        System.out.println("Veículo atribuído ao cliente com sucesso.");
    }

    public static void desvincularCliente(Scanner scanner) {
        DadosClientes.listar();
        if(DadosClientes.listaClientes.isEmpty()) return;
        System.out.print("Digite o ID do cliente dono do veículo: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        Cliente cliente = DadosClientes.buscarId(idCliente);
        if(cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        int index = DadosClientes.listaClientes.indexOf(cliente);   

        ArrayList<Integer> veiculosCliente = cliente.getVeiculos();

        if (veiculosCliente == null || veiculosCliente.isEmpty()) {
            System.out.println("Este cliente não possui veículos vinculados.");
            return;
        }

        System.out.println("Selecione o veículo a ser desvinculado:");
        for (int i = 0; i < veiculosCliente.size(); i++) {
            Veiculo veiculo = buscarId(veiculosCliente.get(i));
            if (veiculo != null) {
                System.out.println("Id: " + veiculo.getId() + " - " + veiculo.getPlaca() + " - Modelo: " + veiculo.getModelo());
            }
        }
        System.out.print("Digite o ID do veículo a ser desvinculado: ");
        int idVeiculo = Integer.parseInt(scanner.nextLine());
        Veiculo veiculo = buscarId(idVeiculo);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }
        veiculosCliente.remove(Integer.valueOf(veiculo.getId()));
        cliente.setVeiculos(veiculosCliente);
        if (index != -1) {
            DadosClientes.listaClientes.set(index, cliente);
        }
        DadosClientes.salvarClientesJson();
        System.out.println("Veículo desvinculado do cliente com sucesso.");
    }
}
