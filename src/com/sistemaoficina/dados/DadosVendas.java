package com.sistemaoficina.dados;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import static com.sistemaoficina.dados.DadosProduto.reduzirEstoque;
import com.sistemaoficina.dto.ItemProduto;
import com.sistemaoficina.dto.OrdemServico;
import com.sistemaoficina.dto.Produto;
import com.sistemaoficina.dto.Venda;
import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe responsável pela gestão das vendas no sistema.
 * <p>
 * Permite registrar vendas, listar vendas, gerar relatórios e excluir registros.
 * <p>
 * @author Riquelme Moreira Campos
 * @version 1.0
 
 */
public class DadosVendas {

    /** Caminho do arquivo JSON utilizado para persistência das vendas. */
    private static final String ARQUIVO_VENDAS = "bd/vendas.json";

    /** Lista estática de vendas carregadas em memória. */
    public static ArrayList<Venda> listaVendas = carregarVendas();

    /**
     * Carrega as vendas do arquivo JSON.
     * Se o arquivo não existir, retorna uma lista vazia.
     *
     * @return Lista de vendas carregada do arquivo ou vazia.
     */
    private static ArrayList<Venda> carregarVendas() {
        try (FileReader reader = new FileReader(ARQUIVO_VENDAS)) {
            Type listaTipo = new TypeToken<ArrayList<Venda>>(){}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de vendas não encontrado. Iniciando lista vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Salva a lista de vendas no arquivo JSON.
     */
    private static void salvarVendas() {
        try (FileWriter writer = new FileWriter(ARQUIVO_VENDAS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaVendas, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar vendas: " + e.getMessage());
        }
    }

    /**
     * Registra uma nova venda a partir da entrada do usuário via terminal.
     * Solicita ID do produto, quantidade e registra a venda.
     *
     * @param scanner Scanner para leitura de dados do usuário.
     */
    public static void registrarVenda(Scanner scanner) {
        DadosProduto.listar();
        System.out.print("Digite o ID do produto vendido: ");
        int idProduto = Integer.parseInt(scanner.nextLine());
        Produto produto = DadosProduto.buscarId(idProduto);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        System.out.print("Quantidade vendida: ");
        int quantidade = Integer.parseInt(scanner.nextLine());
        if (quantidade > produto.getQuantidade()) {
            System.out.println("Estoque insuficiente.");
            return;
        }
        double valorUnitario = produto.getValorVendido();
        reduzirEstoque(idProduto, quantidade);
        Venda venda = new Venda(listaVendas.size() + 1, idProduto, quantidade, valorUnitario, new Date());
        listaVendas.add(venda);
        salvarVendas();
        System.out.printf("Venda registrada. Valor total: R$ %.2f\n", venda.getValorTotal());
    }

    /**
     * Gera e imprime no console um relatório de todas as vendas realizadas,
     * incluindo vendas diretas e produtos utilizados em serviços.
     * Também apresenta totais consolidados.
     */
    public static void gerarRelatorioVendas() {
        double totalVendasDiretas = 0;
        double totalServicosProdutos = 0;
        System.out.println("--- VENDAS DIRETAS ---");
        for (Venda v : listaVendas) {
            System.out.printf("ID: %d | Produto: %d | Quant: %d | Unit: R$ %.2f | Total: R$ %.2f | Data: %s\n",
                    v.getId(), v.getIdProduto(), v.getQuantidadeVendida(), v.getValorUnitario(),
                    v.getValorTotal(), new SimpleDateFormat("dd/MM/yyyy").format(v.getDataVenda()));
            totalVendasDiretas += v.getValorTotal();
        }
        System.out.println("\n--- PRODUTOS UTILIZADOS EM SERVIÇOS ---");
        for (OrdemServico os : DadosOrdemServico.listaOrdemServico) {
            if (os.getProdutosUtilizados() != null) {
                for (ItemProduto item : os.getProdutosUtilizados()) {
                    Produto p = DadosProduto.buscarId(item.getIdProduto());
                    if (p != null) {
                        double subtotal = item.getQuantidade() * p.getValorVendido();
                        System.out.printf("OS %d | Produto: %s | Quant: %d | Unit: R$ %.2f | Subtotal: R$ %.2f\n",
                                os.getId(), p.getNome(), item.getQuantidade(), p.getValorVendido(), subtotal);
                        totalServicosProdutos += subtotal;
                    }
                }
            }
        }
        double totalGeral = totalVendasDiretas + totalServicosProdutos;
        System.out.printf("\nTOTAL VENDAS DIRETAS: R$ %.2f\n", totalVendasDiretas);
        System.out.printf("TOTAL PRODUTOS EM SERVIÇOS: R$ %.2f\n", totalServicosProdutos);
        System.out.printf("TOTAL GERAL: R$ %.2f\n", totalGeral);
    }

    /**
     * Busca uma venda pelo seu ID.
     *
     * @param id Identificador da venda.
     * @return Venda encontrada, ou null se não existir.
     */
    public static Venda buscarId(int id) {
        for (Venda n : listaVendas) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }

    /**
     * Exclui uma venda a partir do ID informado pelo usuário.
     * Exibe relatório antes para facilitar a escolha.
     *
     * @param scanner Scanner para leitura de dados do usuário.
     */
    public static void excluirVenda(Scanner scanner) {
        gerarRelatorioVendas();
        if (listaVendas.isEmpty()) return;
        System.out.print("Digite o número da Despesa que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Venda v = buscarId(indice);
        if (v == null) {
            System.out.println("Despesa inexistente!");
            return;
        }
        listaVendas.remove(v);
        salvarVendas(); //Questão 9
        System.out.println("Venda excluída com sucesso!");
    }
}
