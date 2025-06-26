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
import com.sistemaoficina.dto.Fornecedor;

/**
 * Classe responsável pela manipulação dos dados dos fornecedores. Permite
 * cadastrar, listar, excluir e associar tipos de produto fornecidos. Dados são
 * persistidos em arquivos JSON.
 *
 * @author Riquelme Moreira Campos
 * @version 1.0
 */
public class DadosFornecedores {

    /**
     * Caminho do arquivo JSON de persistência dos fornecedores.
     */
    private static final String ARQUIVO_FORNECEDORES = "bd/fornecedores.json";
    /**
     * Lista estática com todos os fornecedores em memória.
     */
    public static ArrayList<Fornecedor> listaFornecedores = carregarFornecedores();

    /**
     * Carrega a lista de fornecedores a partir do arquivo JSON. Caso o arquivo
     * não exista, retorna uma lista vazia.
     *
     * @return Lista de fornecedores carregada ou vazia em caso de erro.
     */
    public static ArrayList<Fornecedor> carregarFornecedores() {
        try (FileReader reader = new FileReader(ARQUIVO_FORNECEDORES)) {
            Type listaTipo = new TypeToken<ArrayList<Fornecedor>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de fornecedores não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Salva a lista de fornecedores no arquivo JSON.
     */
    public static void salvarFornecedoresJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_FORNECEDORES)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaFornecedores, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Cadastra um novo fornecedor, solicitando informações via Scanner. Permite
     * informar manualmente o(s) tipo(s) de produto fornecido.
     *
     * @param scanner Scanner para leitura dos dados do usuário.
     */
    public static void cadastrar(Scanner scanner) {
        System.out.println("=== Cadastro de Fornecedor ===");
        System.out.print("Nome Real (Razão Social): ");
        String nomeReal = scanner.nextLine();
        System.out.print("Nome Fantasia: ");
        String nomeFantasia = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Tipo de Produto(s) que fornece (ex: Peças, Lubrificantes, etc.): ");
        String tipoProduto = scanner.nextLine();

        Fornecedor fornecedor = new Fornecedor(nomeReal, nomeFantasia, cnpj, telefone, email);
        fornecedor.setTipoProduto(tipoProduto);

        OptionalInt maxId = listaFornecedores.stream().mapToInt(Fornecedor::getId).max();
        fornecedor.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);

        listaFornecedores.add(fornecedor);
        salvarFornecedoresJson();

        System.out.println("Fornecedor cadastrado com sucesso.");
    }

    /**
     * Lista todos os fornecedores cadastrados no sistema.
     */
    public static void listar() {
        if (listaFornecedores.isEmpty()) {
            System.out.println("Não há fornecedores cadastrados!");
            return;
        }
        for (Fornecedor f : listaFornecedores) {
            System.out.println("ID: " + f.getId()
                    + " | Razão Social: " + f.getNomeReal()
                    + " | Nome Fantasia: " + f.getNomeFantasia()
                    + " | CNPJ: " + f.getCnpj()
                    + " | Telefone: " + f.getTelefone()
                    + " | E-mail: " + f.getEmail()
                    + " | Produto(s) fornecido(s): " + f.getTipoProduto());
        }
    }

    /**
     * Busca um fornecedor pelo ID.
     *
     * @param id ID do fornecedor.
     * @return Fornecedor encontrado, ou null se não existir.
     */
    public static Fornecedor buscarId(int id) {
        for (Fornecedor f : listaFornecedores) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    /**
     * Exclui um fornecedor pelo ID, solicitado via Scanner.
     *
     * @param scanner Scanner para leitura do ID a ser excluído.
     */
    public static void excluir(Scanner scanner) {
        listar();
        if (listaFornecedores.isEmpty()) {
            return;
        }
        System.out.print("Digite o ID do fornecedor que deseja excluir: ");
        int id = Integer.parseInt(scanner.nextLine());
        Fornecedor f = buscarId(id);
        if (f == null) {
            System.out.println("Fornecedor não encontrado!");
            return;
        }
        listaFornecedores.remove(f);
        salvarFornecedoresJson();
        System.out.println("Fornecedor excluído com sucesso.");
    }

    /**
     * Permite editar as informações de um fornecedor existente. Solicita o ID,
     * busca o fornecedor, solicita novos dados e salva as alterações no JSON.
     *
     * @param scanner Scanner para entrada de dados do usuário.
     */
    public static void editar(Scanner scanner) {
        listar();
        if (listaFornecedores.isEmpty()) {
            return;
        }
        System.out.print("Digite o ID do fornecedor que deseja editar: ");
        int idFornecedor = Integer.parseInt(scanner.nextLine());
        Fornecedor fornecedor = buscarId(idFornecedor);
        if (fornecedor == null) {
            System.out.println("Fornecedor não encontrado!");
            return;
        }

        System.out.println("Digite os novos dados do fornecedor (deixe em branco para manter o atual):");

        System.out.print("Nome Real [" + fornecedor.getNomeReal() + "]: ");
        String nomeReal = scanner.nextLine();
        if (!nomeReal.isBlank()) {
            fornecedor.setNomeReal(nomeReal);
        }

        System.out.print("Nome Fantasia [" + fornecedor.getNomeFantasia() + "]: ");
        String nomeFantasia = scanner.nextLine();
        if (!nomeFantasia.isBlank()) {
            fornecedor.setNomeFantasia(nomeFantasia);
        }

        System.out.print("CNPJ [" + fornecedor.getCnpj() + "]: ");
        String cnpj = scanner.nextLine();
        if (!cnpj.isBlank()) {
            fornecedor.setCnpj(cnpj);
        }

        System.out.print("Tipo de produto fornecido [" + fornecedor.getTipoProduto() + "]: ");
        String tipoProduto = scanner.nextLine();
        if (!tipoProduto.isBlank()) {
            fornecedor.setTipoProduto(tipoProduto);
        }

        // Se tiver mais campos, repita o padrão acima
        // Atualiza na lista
        int index = listaFornecedores.indexOf(fornecedor);
        if (index != -1) {
            listaFornecedores.set(index, fornecedor);
        }
        salvarFornecedoresJson();
        System.out.println("Fornecedor editado com sucesso.");
    }

}
