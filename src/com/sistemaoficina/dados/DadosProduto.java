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
import com.sistemaoficina.dto.Produto;

public class DadosProduto {

    private static final String ARQUIVO_PRODUTOS = "bd/produtos.json";

    public static ArrayList<Produto> listaProdutos = carregarProdutos();

    public static void salvarProdutosJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_PRODUTOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaProdutos, writer);            
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public static ArrayList<Produto> carregarProdutos() {
        try (FileReader reader = new FileReader(ARQUIVO_PRODUTOS)) {
            Type listaTipo = new TypeToken<ArrayList<Produto>>(){}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de produtos não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    public static void cadastrar(Scanner scanner){
        System.out.println("Cadastrar produto");
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade em estoque: ");
        int qt = Integer.parseInt(scanner.nextLine());
        System.out.print("Valor de compra: ");
        Double valorPago = Double.parseDouble(scanner.nextLine());
        System.out.print("Valor de venda: ");
        Double valorVendido = Double.parseDouble(scanner.nextLine());

        Produto produto = new Produto(qt, nome, valorPago, valorVendido);
        OptionalInt maxId = listaProdutos.stream()
            .mapToInt(Produto::getId)
            .max();
        produto.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);

        listaProdutos.add(produto);
        salvarProdutosJson();
        
        System.out.println("Produto cadastrado com sucesso.");
    }

    public static void editar(Scanner scanner){
        System.out.println("Escolha um produto por Id:");
        listar();
        if(listaProdutos.isEmpty()) return;
        int idProduto = Integer.parseInt(scanner.nextLine());
        Produto prod = buscarId(idProduto);
        if (prod == null) {
            System.out.println("Produto não existente!");
            return;
        }   
        System.out.println("Digite as informações do produto");
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade em estoque: ");
        int qt = Integer.parseInt(scanner.nextLine());
        System.out.print("Valor de compra: ");
        Double valorPago = Double.parseDouble(scanner.nextLine());
        System.out.print("Valor de venda: ");
        Double valorVendido = Double.parseDouble(scanner.nextLine());

        Produto novoProduto = new Produto(qt, nome, valorPago, valorVendido);
        novoProduto.setId(prod.getId());

        int index = listaProdutos.indexOf(prod);

        if (index != -1) {
            listaProdutos.set(index, novoProduto);
        }

        salvarProdutosJson();
        
        System.out.println("Produto editado com sucesso.");
    }
    
    public static void verificarEstoque(Scanner scanner){
        System.out.println("Escolha um produto por Id:");
        listar();
        if(listaProdutos.isEmpty()) return;
        int idProduto = Integer.parseInt(scanner.nextLine());
        Produto prod = buscarId(idProduto);
        if (prod == null) {
            System.out.println("Produto não existente!");
            return;
        }

        if(prod.getQuantidade() > 0){
            System.out.println("Produto em estoque!");
        } else {
            System.out.println("Produto sem estoque!");
        }
        System.out.println("Produto: " + prod.getNome() + " | Quantidade em estoque: " + prod.getQuantidade());
    }

    public static void listar(){
        if(listaProdutos.isEmpty()){
            System.out.println("Não há produtos para serem listados!");
        }
        for(Produto p : listaProdutos){
            System.out.println("Id: " + p.getId() + " - " + p.getNome() + " - Estoque: " + p.getQuantidade());
        }
    }
    
    public static Produto buscarId(int id){
        for(Produto p : listaProdutos){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }
    
    public static void excluir(Scanner scanner) {
        listar();
        if(listaProdutos.isEmpty()) return;
        System.out.print("Digite o número do produto que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Produto p = buscarId(indice);
        if(p == null){
            System.out.println("Produto não existente!");
            return;
        }

        listaProdutos.remove(p);
        salvarProdutosJson(); 
        System.out.println("Produto excluido com sucesso.");
    }
}
