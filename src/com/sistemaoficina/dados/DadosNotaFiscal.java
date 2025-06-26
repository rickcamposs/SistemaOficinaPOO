package com.sistemaoficina.dados;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sistemaoficina.dto.ItemProduto;
import com.sistemaoficina.dto.NotaFiscal;
import com.sistemaoficina.dto.OrdemServico;
import com.sistemaoficina.dto.Produto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

/**
 * Classe responsável por gerenciar as notas fiscais do sistema.
 * <p>
 * Permite criar, listar, buscar, imprimir e salvar notas fiscais em arquivo JSON.
 * <p>
 * 
 * @author Riquelme Moreira Campos
 * @version 1.0
 */
public class DadosNotaFiscal {

    /** Caminho do arquivo JSON de persistência das notas fiscais. */
    private static final String ARQUIVO_NF = "bd/notafiscal.json";

    /** Lista estática de todas as notas fiscais carregadas em memória. */
    public static ArrayList<NotaFiscal> listaNF = carregarNF();

    /**
     * Carrega a lista de notas fiscais do arquivo JSON.
     * Se o arquivo não existir ou houver erro, retorna uma lista vazia.
     *
     * @return Lista de notas fiscais carregada do arquivo ou vazia.
     */
    public static ArrayList<NotaFiscal> carregarNF() {
        try (FileReader reader = new FileReader(ARQUIVO_NF)) {
            Type listaTipo = new TypeToken<ArrayList<NotaFiscal>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de Nota Fiscal não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Salva a lista de notas fiscais no arquivo JSON.
     */
    public static void salvarNFJson() {
        try (FileWriter writerNF = new FileWriter(ARQUIVO_NF)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaNF, writerNF);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Lista todas as notas fiscais cadastradas no sistema.
     */
    public static void listar() {
        if (listaNF.isEmpty()) {
            System.out.println("Não existem Notas Fiscais para serem listadas!");
        }
        for (NotaFiscal nf : listaNF) {
            System.out.println("Notas Fiscais");
            System.out.println("ID: " + nf.getId()
                    + " -> Descrição: " + nf.getDescricao()
                    + " | Data: " + nf.getData()
                    + " | Valor Total do Serviço: "
                    + DadosOrdemServico.buscarId(nf.getIdOrdemServico()).getValorEstimado());
        }
    }

    /**
     * Busca uma nota fiscal pelo seu ID.
     *
     * @param id Identificador da nota fiscal.
     * @return Objeto NotaFiscal encontrado ou null se não existir.
     */
    public static NotaFiscal buscarId(int id) {
        for (NotaFiscal n : listaNF) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }

    /**
     * Cria uma nova nota fiscal vinculada a uma ordem de serviço.
     *
     * @param scanner Scanner para entrada dos dados via terminal.
     */
    public static void criarNF(Scanner scanner) {
        System.out.println("Crie a Nota Fiscal");
        System.out.print("Descreva o que foi utilizado no serviço realizado: ");
        String descricao = scanner.nextLine();
        System.out.println("Selecione o ID da Ordem de Serviço referente a essa Nota Fiscal: ");
        DadosOrdemServico.listar();
        if (DadosOrdemServico.listaOrdemServico.isEmpty()) {
            return;
        }
        int idOrdemServico = Integer.parseInt(scanner.nextLine());
        OrdemServico or = DadosOrdemServico.buscarId(idOrdemServico);
        if (or == null) {
            System.out.println("Ordem de Serviço inexistente");
            return;
        }

        NotaFiscal f = new NotaFiscal(new Date(), descricao, idOrdemServico);
        OptionalInt maxId = listaNF.stream()
                .mapToInt(NotaFiscal::getId)
                .max();
        f.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);
        listaNF.add(f);
        salvarNFJson();

        System.out.println("NF salva com sucesso.");
    }

    /**
     * Solicita ao usuário um ID e imprime a nota fiscal correspondente.
     * Exibe um resumo antes da seleção.
     *
     * @param scanner Scanner para entrada dos dados via terminal.
     */
    public static void imprimirNF(Scanner scanner) {
        if (listaNF.isEmpty()) {
            System.out.println("Não há notas fiscais para imprimir.");
            return;
        }
        listar(); // Mostra resumo para o usuário escolher
        System.out.print("Digite o ID da nota fiscal que deseja imprimir: ");
        int id = Integer.parseInt(scanner.nextLine());
        NotaFiscal nf = buscarId(id);
        if (nf == null) {
            System.out.println("Nota Fiscal não encontrada.");
        } else {
            imprimirNotaFiscal(nf); // Imprime o detalhamento completo
        }
    }

    /**
     * Imprime uma nota fiscal detalhada no console, incluindo serviços, produtos e totais.
     *
     * @param nf NotaFiscal a ser impressa.
     */
    public static void imprimirNotaFiscal(NotaFiscal nf) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("========================================");
        System.out.println("            OFICINA MILHO VERDE         ");
        System.out.println("           NOTA FISCAL SIMPLIFICADA     ");
        System.out.println("========================================");
        System.out.println("Data: " + sdf.format(nf.getData()));
        System.out.println("----------------------------------------");
        System.out.println("ID NF: " + nf.getId());
        System.out.println("Descrição: " + nf.getDescricao());
        OrdemServico os = DadosOrdemServico.buscarId(nf.getIdOrdemServico());
        if (os != null) {
            System.out.printf("Valor do Serviço: R$ %.2f\n", os.getValorEstimado());
            List<ItemProduto> itens = os.getProdutosUtilizados();
            if (itens != null && !itens.isEmpty()) {
                System.out.println("Produtos Utilizados:");
                double totalProdutos = 0;
                for (ItemProduto item : itens) {
                    Produto p = DadosProduto.buscarId(item.getIdProduto());
                    if (p != null) {
                        double subtotal = p.getValorVendido() * item.getQuantidade();
                        System.out.printf("- %s (Qtd: %d | Unitário: R$ %.2f) -> Subtotal: R$ %.2f\n",
                                p.getNome(), item.getQuantidade(), p.getValorVendido(), subtotal);
                        totalProdutos += subtotal;
                    }
                }
                double totalGeral = os.getValorEstimado() + totalProdutos;
                System.out.printf("Total Produtos: R$ %.2f\n", totalProdutos);
                System.out.printf("TOTAL GERAL: R$ %.2f\n", totalGeral);
            } else {
                System.out.println("Nenhum produto registrado na OS.");
            }
        } else {
            System.out.println("Ordem de Serviço não encontrada.");
        }
        System.out.println("----------------------------------------");
        System.out.println("Obrigado pela preferência!");
        System.out.println("========================================\n");
    }
}
