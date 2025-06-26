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

/**
 * Classe utilitária para gerenciamento de produtos no sistema.
 * <p>
 * Responsável por cadastrar, editar, listar, excluir, buscar e persistir produtos em arquivo JSON.
 * </p>
 * 
 * @author Seu Nome
 * @version 1.0
 */
public class DadosProduto {

    /** Caminho do arquivo JSON onde os produtos são armazenados. */
    private static final String ARQUIVO_PRODUTOS = "bd/produtos.json";
    
    /** Lista estática de produtos carregada na memória. */
    public static ArrayList<Produto> listaProdutos = carregarProdutos();

    /**
     * Salva a lista atual de produtos no arquivo JSON.
     */
    public static void salvarProdutosJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_PRODUTOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaProdutos, writer);            
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de produtos do arquivo JSON.
     * Caso não exista ou ocorra erro, retorna uma lista vazia.
     * 
     * @return Lista de produtos carregada ou vazia.
     */
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

    /**
     * Cadastra um novo produto no sistema a partir dos dados digitados.
     *
     * @param scanner Scanner para entrada dos dados do usuário.
     */
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

    /**
     * Edita os dados de um produto já cadastrado.
     *
     * @param scanner Scanner para entrada dos dados do usuário.
     */
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
    
    /**
     * Verifica o estoque de um produto selecionado.
     *
     * @param scanner Scanner para entrada do usuário.
     */
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

    /**
     * Lista todos os produtos cadastrados.
     */
    public static void listar(){
        if(listaProdutos.isEmpty()){
            System.out.println("Não há produtos para serem listados!");
        }
        for(Produto p : listaProdutos){
            System.out.println("Id: " + p.getId() + " - " + p.getNome() + " - Estoque: " + p.getQuantidade());
        }
    }
    
    /**
     * Busca um produto pelo seu ID.
     *
     * @param id ID do produto.
     * @return Produto encontrado ou null se não existir.
     */
    public static Produto buscarId(int id){
        for(Produto p : listaProdutos){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }
    
    /**
     * Reduz o estoque de um produto.
     *
     * @param idProduto ID do produto.
     * @param quantidade Quantidade a ser reduzida.
     */
    public static void reduzirEstoque(int idProduto, int quantidade) {
        Produto p = buscarId(idProduto);
        if (p != null && p.getQuantidade() >= quantidade) {
            p.setQuantidade(p.getQuantidade() - quantidade);
            salvarProdutosJson();
        }
    }
    
    /**
     * Exclui um produto do sistema.
     *
     * @param scanner Scanner para entrada do usuário.
     */
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
