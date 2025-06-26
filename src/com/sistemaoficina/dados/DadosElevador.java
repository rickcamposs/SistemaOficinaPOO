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
import com.sistemaoficina.dto.Elevador;
import com.sistemaoficina.dto.Funcionario;
import com.sistemaoficina.dto.OrdemServico;
import com.sistemaoficina.dto.Veiculo;

/**
 * Classe responsável pelo gerenciamento dos elevadores da oficina. Permite
 * cadastrar, listar, associar/desassociar elevadores a ordens de serviço e
 * persistir os dados em arquivo JSON.
 *
 * @author Riquelme Moreira Campos
 * @version 1.0
 */
public class DadosElevador {

    /**
     * Caminho do arquivo JSON para persistência dos elevadores.
     */
    private static final String ARQUIVO_ELEVADOR = "bd/elevador.json";
    /**
     * Lista estática com todos os elevadores em memória.
     */
    public static ArrayList<Elevador> listaElevador = carregarElevadores();

    /**
     * Salva a lista de elevadores no arquivo JSON.
     */
    public static void salvarElevadorJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_ELEVADOR)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaElevador, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de elevadores a partir do arquivo JSON.
     *
     * @return Uma lista de elevadores carregada do arquivo ou uma lista vazia
     * em caso de erro.
     */
    public static ArrayList<Elevador> carregarElevadores() {
        try (FileReader reader = new FileReader(ARQUIVO_ELEVADOR)) {
            Type listaTipo = new TypeToken<ArrayList<Elevador>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de produtos não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Atribui uma ordem de serviço a um elevador disponível de um determinado
     * tipo.
     *
     * @param scanner Scanner utilizado para entrada de dados do usuário.
     */
    public static void atribuirOrdemServico(Scanner scanner) {
        DadosOrdemServico.listar();
        if (DadosOrdemServico.listaOrdemServico.isEmpty()) {
            return;
        }
        System.out.print("Selecione a ordem de serviço para atribuir ao elevador: ");
        int idOrdemServico = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        OrdemServico os = DadosOrdemServico.buscarId(idOrdemServico);
        if (os == null) {
            System.out.println("Ordem de serviço não encontrada.");
            return;
        }
        System.out.println("Selecione o tipo do elevador: ");
        System.out.println("1 - Elevador geral");
        System.out.println("2 - Elevador de alinhamento e balanceamento");
        Integer op = scanner.nextInt();
        scanner.nextLine();

        String tipoElevador;
        switch (op) {
            case 1 ->
                tipoElevador = "Elevador geral";
            case 2 ->
                tipoElevador = "Elevador de alinhamento e balanceamento";
            default -> {
                System.out.println("Opção Inválida");
                return;
            }
        }
        Elevador elevador = buscarElevadorLivrePorTipo(tipoElevador);
        if (elevador == null) {
            System.out.println("Não há elevadores livres do tipo: " + tipoElevador);
            return;
        }

        int index = listaElevador.indexOf(elevador);
        elevador.setIdOrdemServico(idOrdemServico);
        listaElevador.set(index, elevador);
        salvarElevadorJson();

        System.out.println("Elevador atribuído com sucesso!");
    }

    /**
     * Desvincula uma ordem de serviço de um elevador selecionado.
     *
     * @param scanner Scanner utilizado para entrada de dados do usuário.
     */
    public static void desvincularOrdemServico(Scanner scanner) {
        listar();
        if (listaElevador.isEmpty()) {
            return;
        }
        System.out.print("Selecione o elevador para desvincular a ordem de serviço: ");
        int idElevador = scanner.nextInt();
        scanner.nextLine();
        if (idElevador < 0 || idElevador >= listaElevador.size()) {
            System.out.println("Elevador inválido.");
            return;
        }
        Elevador elevador = listaElevador.get(idElevador);
        int index = listaElevador.indexOf(elevador);
        elevador.setIdOrdemServico(null);
        listaElevador.set(index, elevador);
        salvarElevadorJson();
        System.out.println("Ordem de serviço desvinculada com sucesso!");
    }

    /**
     * Busca um elevador livre (sem ordem de serviço) do tipo informado.
     *
     * @param tipoElevador Tipo do elevador desejado.
     * @return Elevador livre encontrado, ou null caso não exista nenhum
     * disponível.
     */
    public static Elevador buscarElevadorLivrePorTipo(String tipoElevador) {
        for (Elevador elevador : listaElevador) {
            if (elevador.getTipoElevador().equals(tipoElevador) && elevador.getIdOrdemServico() == null) {
                return elevador;
            }
        }
        return null;
    }

    /**
     * Lista todos os elevadores cadastrados e suas ordens de serviço associadas
     * (se houver).
     */
    public static void listar() {
        if (listaElevador.isEmpty()) {
            System.out.println("Não há elevadores para serem listados!");
            return; // <-- importante!
        }
        int i = 0;
        for (Elevador e : listaElevador) {
            if (e.getIdOrdemServico() == null) {
                System.out.println("Elevador (" + i + "): " + e.getTipoElevador() + " - Não vinculado a nenhuma ordem de serviço.");
                i++;
                continue;
            }
            OrdemServico os = DadosOrdemServico.buscarId(e.getIdOrdemServico());
            if (os == null) {
                System.out.println("Elevador (" + i + "): " + e.getTipoElevador() + " - Ordem de serviço associada não encontrada (pode ter sido excluída).");
                i++;
                continue;
            }
            Funcionario f = DadosFuncionario.buscarId(os.getIdFuncionarioResponsavel());
            Veiculo v = DadosVeiculo.buscarId(os.getIdVeiculo());
            String nomeFuncionario = (f != null) ? f.getNome() : "Desconhecido";
            String infoVeiculo = (v != null) ? (v.getPlaca() + " " + v.getModelo()) : "Veículo não encontrado";
            System.out.println("Elevador (" + i + "): " + e.getTipoElevador()
                    + " - Funcionário responsável: " + nomeFuncionario
                    + " - Ordem de Serviço: " + e.getIdOrdemServico()
                    + " - Veículo: " + infoVeiculo
            );
            i++;
        }
    }

    /**
     * Inicializa a lista de elevadores com um conjunto padrão, caso a lista
     * esteja vazia.
     */
    public static void inicilizarElevadores() {
        if (listaElevador.isEmpty()) {
            listaElevador.add(new Elevador("Elevador geral"));
            listaElevador.add(new Elevador("Elevador geral"));
            listaElevador.add(new Elevador("Elevador de alinhamento e balanceamento"));
            salvarElevadorJson();
            System.out.println("Elevadores iniciais criados.");
        } else {
            System.out.println("Elevadores já inicializados.");
        }
    }
}
