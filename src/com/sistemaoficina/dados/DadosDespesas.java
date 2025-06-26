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
import com.sistemaoficina.dto.Despesa;
import com.sistemaoficina.dto.ItemProduto;
import com.sistemaoficina.dto.OrdemServico;
import com.sistemaoficina.dto.Produto;
import com.sistemaoficina.dto.Venda;
import com.sistemaoficina.enums.CategoriaDespesa;
import java.util.Calendar;
import java.util.Date;
import java.util.OptionalInt;

/**
 * Classe responsável pelo gerenciamento de despesas do sistema.
 * Permite cadastrar, editar, excluir, listar despesas e gerar o balanço mensal.
 * Os dados são persistidos em arquivo JSON.
 * 
 * @author Riquelme Moreira Campos
 * @version 1.0
 */
public class DadosDespesas {

    /** Caminho do arquivo JSON de persistência das despesas. */
    private static final String ARQUIVO_DESPESA = "bd/despesas.json";

    /** Lista estática de todas as despesas em memória. */
    public static ArrayList<Despesa> listaDespesa = carregarDespesa();

    /**
     * Carrega a lista de despesas a partir do arquivo JSON.
     * Caso o arquivo não exista ou haja erro, retorna uma lista vazia.
     *
     * @return Lista de despesas carregada do arquivo ou vazia.
     */
    public static ArrayList<Despesa> carregarDespesa() {
        try (FileReader reader = new FileReader(ARQUIVO_DESPESA)) {
            Type listaTipo = new TypeToken<ArrayList<Despesa>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo Despesa não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Salva a lista de despesas no arquivo JSON.
     */
    public static void salvarDespesaJson() {
        try (FileWriter writerDespesa = new FileWriter(ARQUIVO_DESPESA)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaDespesa, writerDespesa);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Lista todas as despesas cadastradas no sistema.
     */
    public static void listar() {
        if (listaDespesa.isEmpty()) {
            System.out.println("Não despesas para serem listados!");
        }
        for (Despesa d : listaDespesa) {
            System.out.println("DESPESAS");
            System.out.println(
                    "ID: " + d.getId() +
                    " -> Nome: " + d.getNome() +
                    " | Data: " + d.getData() +
                    " | Descrição: " + d.getDescricao() +
                    " | Valor: " + d.getValor() +
                    " | Tipo: " + d.getCategoria()
            );
        }
    }

    /**
     * Adiciona uma nova despesa no sistema.
     *
     * @param scanner Scanner para entrada dos dados via terminal.
     */
    public static void adicionarDespesa(Scanner scanner) {
        System.out.println("Adicione a Despesa que Deseja");
        System.out.print("Nome da Despesa: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da Despesa: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Faça uma Descrição da Despesa: ");
        String descricao = scanner.nextLine();
        System.out.print("Selecione o Tipo de Despesa: ");
        CategoriaDespesa categoria = selecionarCategoria(scanner);

        Despesa financa = new Despesa(nome, valor, new Date(), descricao, categoria);
        OptionalInt maxId = listaDespesa.stream()
            .mapToInt(Despesa::getId)
            .max();
        financa.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);
        listaDespesa.add(financa);
        salvarDespesaJson();

        System.out.println("Despesas salvas com sucesso.");
    }

    /**
     * Exibe as opções e permite selecionar a categoria da despesa.
     *
     * @param scanner Scanner para entrada dos dados via terminal.
     * @return CategoriaDespesa selecionada.
     */
    public static CategoriaDespesa selecionarCategoria(Scanner scanner) {
        System.out.println("Selecione o tipo de combustível do veiculo:");
        System.out.println("1 - Despesas Gerais");
        System.out.println("2 - Despesas de Contas");
        System.out.println("3 - Despesas de Funcionários");
        System.out.println("4 - Despesas de Pecas");
        int categoriaEscolhida = scanner.nextInt();
        scanner.nextLine();
        return switch (categoriaEscolhida) {
            case 1 -> CategoriaDespesa.DESPESAS_GERAIS;
            case 2 -> CategoriaDespesa.DESPESAS_CONTAS;
            case 3 -> CategoriaDespesa.DESPESAS_FUNCIONARIO;
            case 5 -> CategoriaDespesa.DESPESAS_PECAS;
            default -> {
                System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
                yield selecionarCategoria(scanner);
            }
        };
    }

    /**
     * Retorna o texto descritivo para uma categoria de despesa.
     *
     * @param categoria CategoriaDespesa desejada.
     * @return String com a descrição da categoria.
     */
    public static String getTextoCategoria(CategoriaDespesa categoria) {
        if (categoria == null) {
            return "Combustível Indefinido";
        }
        return switch (categoria) {
            case DESPESAS_GERAIS -> "Despesas Gerais";
            case DESPESAS_FUNCIONARIO -> "Despesas de Funcionarios";
            case DESPESAS_PECAS -> "Despesas de Peças";
            case DESPESAS_CONTAS -> "Despesas de Contas";
        };
    }

    /**
     * Busca uma despesa pelo seu ID.
     *
     * @param id Identificador da despesa.
     * @return Objeto Despesa encontrado ou null se não existir.
     */
    public static Despesa buscarId(int id) {
        for (Despesa f : listaDespesa) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    /**
     * Exclui uma despesa do sistema pelo ID informado pelo usuário.
     *
     * @param scanner Scanner para entrada dos dados via terminal.
     */
    public static void excluirDespesa(Scanner scanner) {
        listar();
        if (listaDespesa.isEmpty()) return;
        System.out.print("Digite o número da Despesa que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Despesa f = buscarId(indice);
        if (f == null) {
            System.out.println("Despesa inexistente!");
            return;
        }
        listaDespesa.remove(f);
        salvarDespesaJson();
        System.out.println("Despesa excluida com sucesso.");
    }

    /**
     * Edita uma despesa já cadastrada no sistema.
     *
     * @param scanner Scanner para entrada dos dados via terminal.
     */
    public static void editar(Scanner scanner) {
        System.out.println("Escolha um veiculo por Id:");
        listar();
        if (listaDespesa.isEmpty()) return;
        int idVeiculo = Integer.parseInt(scanner.nextLine());
        Despesa despesa = buscarId(idVeiculo);
        if (despesa == null) {
            System.out.println("Veículo não existente!");
            return;
        }
        System.out.println("Digite as informações do Veículo");
        System.out.print("Nome da Despesa: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da Despesa: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Categoria da Despesa: ");
        CategoriaDespesa categoria = selecionarCategoria(scanner);

        Despesa novaDespesa = new Despesa(nome, valor, new Date(), descricao, categoria);
        novaDespesa.setId(despesa.getId());
        int index = listaDespesa.indexOf(despesa);
        if (index != -1) {
            listaDespesa.set(index, novaDespesa);
        }
        salvarDespesaJson();

        System.out.println("Veículo editado com sucesso.");
    }

    /**
     * Gera o balanço mensal de receitas, despesas e saldo final.
     * Leva em conta vendas diretas, produtos em serviços, serviços prestados e despesas.
     *
     * @param mes Mês do balanço (1-12).
     * @param ano Ano do balanço.
     */
    public static void gerarBalancoMensal(int mes, int ano) {
        double totalVendas = 0;
        double totalProdutosEmServicos = 0;
        double totalServicos = 0;
        double totalDespesas = 0;
        Calendar cal = Calendar.getInstance();

        // Vendas Diretas
        for (Venda v : DadosVendas.listaVendas) {
            if (v.getDataVenda() != null) {
                cal.setTime(v.getDataVenda());
                if (cal.get(Calendar.MONTH) == mes - 1 && cal.get(Calendar.YEAR) == ano) {
                    totalVendas += v.getValorTotal();
                }
            }
        }

        // Produtos utilizados em Ordens de Serviço + valor estimado do serviço
        for (OrdemServico os : DadosOrdemServico.listaOrdemServico) {
            Date dataCriacao = os.getDataServico();
            if (dataCriacao != null) {
                cal.setTime(dataCriacao);
                if (cal.get(Calendar.MONTH) == mes - 1 && cal.get(Calendar.YEAR) == ano) {
                    // Soma valor do serviço
                    totalServicos += os.getValorEstimado();
                    // Soma produtos utilizados
                    if (os.getProdutosUtilizados() != null) {
                        for (ItemProduto item : os.getProdutosUtilizados()) {
                            Produto p = DadosProduto.buscarId(item.getIdProduto());
                            if (p != null) {
                                totalProdutosEmServicos += item.getQuantidade() * p.getValorVendido();
                            }
                        }
                    }
                }
            }
        }

        // Despesas
        for (Despesa d : listaDespesa) {
            if (d.getData() != null) {
                cal.setTime(d.getData());
                if (cal.get(Calendar.MONTH) == mes - 1 && cal.get(Calendar.YEAR) == ano) {
                    totalDespesas += d.getValor();
                }
            }
        }

        double totalReceitas = totalVendas + totalProdutosEmServicos + totalServicos;
        double saldoFinal = totalReceitas - totalDespesas;

        // Impressão do balanço
        System.out.println("====== BALANÇO MENSAL ======");
        System.out.printf("MÊS: %02d/%d\n", mes, ano);
        System.out.println("-------------------------------------");
        System.out.printf("RECEITA - Vendas Diretas: R$ %.2f\n", totalVendas);
        System.out.printf("RECEITA - Produtos em Serviços: R$ %.2f\n", totalProdutosEmServicos);
        System.out.printf("RECEITA - Serviços Prestados: R$ %.2f\n", totalServicos);
        System.out.printf("TOTAL DE RECEITAS: R$ %.2f\n", totalReceitas);
        System.out.println("-------------------------------------");
        System.out.printf("TOTAL DE DESPESAS: R$ %.2f\n", totalDespesas);
        System.out.println("-------------------------------------");
        System.out.printf("SALDO FINAL DO MÊS: R$ %.2f\n", saldoFinal);
        System.out.println("=====================================");
    }
}
