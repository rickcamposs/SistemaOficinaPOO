package com.sistemaoficina.dados;
//package com.sistemaoficina.comparator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sistemaoficina.comparator.ComparatorCliente;
import com.sistemaoficina.dto.Cliente;
import com.sistemaoficina.dto.Veiculo;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class DadosClientes {
    private static final String ARQUIVO_CLIENTES = "bd/clientes.json";

    public static ArrayList<Cliente> listaClientes = carregarClientes();

    /**
     * Carrega a lista de clientes a partir do arquivo JSON.
     * Caso o arquivo não exista ou ocorra um erro na leitura, retorna uma lista
     * vazia.
     * 
     * @return A lista de clientes carregada do arquivo ou uma lista vazia se
     *         ocorrer erro.
     */
    public static ArrayList<Cliente> carregarClientes() {
        try (FileReader reader = new FileReader(ARQUIVO_CLIENTES)) {
            Type listaTipo = new TypeToken<ArrayList<Cliente>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de clientes não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    public static void salvarClientesJson() {
        try (FileWriter writerClientes = new FileWriter(ARQUIVO_CLIENTES)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaClientes, writerClientes);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static void cadastrar(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF (pseudo-anonimizado): ");
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(nome, endereco, telefone, email, cpf);
        OptionalInt maxId = listaClientes.stream()
            .mapToInt(Cliente::getId)
            .max();
        cliente.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);
        listaClientes.add(cliente);
        salvarClientesJson();
        System.out.println("Cliente cadastrado com sucesso.");
    }

    public static void editar(Scanner scanner){
        System.out.println("Escolha um cliente por Id:");
        listar();
        if(listaClientes.isEmpty()) return;
        int idCliente = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarId(idCliente);
        if(cliente == null){
            System.out.println("Cliente não existente!");
            return;
        }
        System.out.println("Digite as informações do cliente: ");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF (pseudo-anonimizado): ");
        String cpf = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, endereco, telefone, email, cpf);
        novoCliente.setId(cliente.getId());

        int index = listaClientes.indexOf(cliente);

        if (index != -1) {
            listaClientes.set(index, novoCliente);
        }

        salvarClientesJson();
        System.out.println("Cliente editado com sucesso.");
        

    }

    public static void listar() {
        if(listaClientes.isEmpty()){
            System.out.println("Não há clientes para serem listados!");
        }
        for (Cliente c : listaClientes) {
            System.out.println("Id: " + c.getId() + " - " + c.getNome());
        }
    }

    public static Cliente buscarId(int id) {
        for(Cliente c : listaClientes){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public static void excluir(Scanner scanner) {
        listar();
        if(listaClientes.isEmpty()) return;
        System.out.print("Digite o número do cliente que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Cliente c = buscarId(indice);
        if(c == null){
            System.out.println("Cliente não existente!");
            return;
        }

        listaClientes.remove(c);
        salvarClientesJson();
        
        System.out.println("Cliente excluido com sucesso.");
    }

    public static void listarVeiculosCliente(Cliente cliente) {
        if (cliente.getVeiculos().isEmpty()) {
            System.out.println("Este cliente não possui veículos cadastrados.");
        } else {
            System.out.println("Veículos do cliente " + cliente.getNome() + ":");
            for (int idVeiculo : cliente.getVeiculos()) {
                Veiculo veiculo = DadosVeiculo.buscarId(idVeiculo);
                if (veiculo != null) {
                    System.out.println("Id: " + veiculo.getId() + " - Placa: " + veiculo.getPlaca() + " - Modelo: " + veiculo.getModelo());
                }
            }
        }
        
    }
    
    //Questão 16
    
    public static void listaCrescente(){
        List<Cliente> listaCrescente = new ArrayList<>(listaClientes);
        
        Collections.sort(listaCrescente, ComparatorCliente.usuarioCrescente);
        
        listaCrescente.forEach(c -> System.out.println("Cliente " + "Nome: " + c.getNome() + " " + "ID: " + c.getId()));
        
    }
    
    public static void listaDecrescente(){
        List<Cliente> listaDecrescente = new ArrayList<>(listaClientes);
        
        Collections.sort(listaDecrescente, ComparatorCliente.usuarioDecrescente);
        
        listaDecrescente.forEach(d -> System.out.println("Cliente " + "Nome: " + d.getNome() + " " + "ID: " + d.getId()));
        
    }
    
    //final 16
    
    //Questão 17
    
    public static void buscarIdIterator(Scanner scanner){
        System.out.println("Digite o ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Iterator<Cliente> iterator = listaClientes.iterator();
        while(iterator.hasNext()){
            Cliente c = iterator.next();
            if(c.getId() == id){
                System.out.println(c.getId() + " - " + c.getNome());
                return;
            } 
        }
        
        System.out.println("Usuário Não Encontrado");
    }
    
    public static void buscaIdBinary(Scanner scanner){
        System.out.println("Digite o ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = new Cliente();
        cliente.setId(id);
        List<Cliente> listaOrdenadaCrescente = new ArrayList<>(listaClientes);
        Collections.sort(listaOrdenadaCrescente, ComparatorCliente.usuarioCrescente);
        Integer pos = Collections.binarySearch(listaOrdenadaCrescente, cliente, Comparator.comparing(Cliente::getId));
        
        if(pos >= 0){
            Cliente usr = listaOrdenadaCrescente.get(pos);
            System.out.println("Usuario Encontrado: " + usr.getId() + " - " + usr.getNome());
        } else {
            System.out.println("Usuário Não Encontrado");
        }
    }
}