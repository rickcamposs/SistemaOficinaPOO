package com.sistemaoficina.dados;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sistemaoficina.dto.Funcionario;
import com.sistemaoficina.dto.Ponto;

/**
 * Classe responsável pela manipulação dos dados de ponto dos funcionários.
 * Permite abrir e fechar ponto, listar pontos por funcionário e persistir dados em JSON.
 * 
 * @author Riquelme Moreira Campos
 * @version 1.0
 */
public class DadosPonto {

    /** Caminho do arquivo JSON de persistência dos pontos. */
    private static final String ARQUIVO_PONTO = "bd/ponto.json";

    /** Lista estática com todos os pontos em memória. */
    public static ArrayList<Ponto> listaPontos = carregarPontos();

    /**
     * Carrega a lista de pontos a partir do arquivo JSON.
     * Caso o arquivo não exista, retorna uma lista vazia.
     * 
     * @return Lista de pontos carregada ou vazia em caso de erro.
     */
    public static ArrayList<Ponto> carregarPontos() {
        try (FileReader reader = new FileReader(ARQUIVO_PONTO)) {
            Type listaTipo = new TypeToken<ArrayList<Ponto>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de ponto não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Salva a lista de pontos no arquivo JSON.
     */
    public static void salvarPontosJson() {
        try (FileWriter writerPonto = new FileWriter(ARQUIVO_PONTO)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaPontos, writerPonto);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Abre um novo ponto (registro de entrada) para o funcionário informado.
     * Não permite abrir mais de um ponto sem fechar o anterior.
     * 
     * @param f Funcionário que está batendo o ponto.
     */
    public static void abrirPonto(Funcionario f){        
        for (Ponto p : listaPontos) {
            if (p.getIdFuncionario() == f.getId() && p.getDataSaida() == null) {
                System.out.println("Ponto já aberto para o funcionário: " + f.getNome());
                return;
            }
        }
        Ponto ponto = new Ponto(listaPontos.size() + 1, new Date(), null, f.getId());
        listaPontos.add(ponto);
        salvarPontosJson();
        System.out.println("Ponto aberto para o funcionário: " + f.getNome() + " em " + ponto.getDataEntrada());
    }

    /**
     * Fecha o ponto (registra a saída) do funcionário informado.
     * Só fecha se existir um ponto aberto.
     * 
     * @param f Funcionário que está batendo o ponto de saída.
     */
    public static void fecharPonto(Funcionario f){
        for (Ponto p : listaPontos) {
            if (p.getIdFuncionario() == f.getId() && p.getDataSaida() == null) {
                p.setDataSaida(new Date());
                salvarPontosJson();
                System.out.println("Ponto fechado para o funcionário: " + f.getNome() + " em " + p.getDataSaida());
                return;
            }
        }
        System.out.println("Nenhum ponto aberto encontrado para o funcionário: " + f.getNome());
    }

    /**
     * Lista todos os pontos de um funcionário em uma data específica.
     * Solicita o ID do funcionário e a data de entrada.
     * 
     * @param sc Scanner para leitura dos dados do usuário.
     */
    public static void listarPontosPorUsuario(Scanner sc) {
        DadosFuncionario.listar();
        if(DadosFuncionario.listaFuncionarios.isEmpty()) return;
        System.out.print("Digite o ID do funcionário: ");
        int idFuncionario = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite a data de entrada (dd/MM/yyyy): ");
        String dataEntradaStr = sc.nextLine();
        Date dataEntrada;
        try {
            dataEntrada = new SimpleDateFormat("dd/MM/yyyy").parse(dataEntradaStr);
        } catch (ParseException e) {
            System.out.println("Data inválida. Por favor, use o formato dd/MM/yyyy.");
            return;
        }
        ArrayList<Ponto> p = buscarPontoPorIdEDataEntrada(idFuncionario, dataEntrada);
        if (p.isEmpty()) {
            System.out.println("Nenhum ponto encontrado para o ID: " + idFuncionario + " na data " + dataEntradaStr);
            return;
        }
        Funcionario funcionario = DadosFuncionario.buscarId(idFuncionario);
        System.out.println("Ponto(s) do Funcionário " + funcionario.getNome() + " na data " + dataEntradaStr + ":");
        for (Ponto ponto : p) {
            System.out.println("ID do Ponto: " + ponto.getId());
            System.out.println("Funcionário: " + funcionario.getId() + " / " + funcionario.getNome());
            System.out.println("Data de Entrada: " + ponto.getDataEntrada());
            if (ponto.getDataSaida() != null) {
                System.out.println("Data de Saída: " + ponto.getDataSaida());
            } else {
                System.out.println("Ponto ainda aberto, sem data de saída.");
            }
        }
    }

    /**
     * Busca todos os registros de ponto de um funcionário em uma data específica.
     * 
     * @param id ID do funcionário.
     * @param dataEntrada Data da entrada (será comparada apenas a data, não a hora).
     * @return Lista de pontos encontrados para aquele funcionário e data.
     */
    private static ArrayList<Ponto> buscarPontoPorIdEDataEntrada(int id, Date dataEntrada) {
        ArrayList<Ponto> pontosEncontrados = new ArrayList<>();
        for (Ponto p : listaPontos) {
            if (p.getIdFuncionario() == id && mesmaData(p.getDataEntrada(), dataEntrada)) {
                pontosEncontrados.add(p);
            }
        }
        return pontosEncontrados;
    }

    /**
     * Verifica se duas datas possuem o mesmo dia, mês e ano (ignorando a hora).
     * 
     * @param d1 Primeira data.
     * @param d2 Segunda data.
     * @return true se forem no mesmo dia, false caso contrário.
     */
    private static boolean mesmaData(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        return c1.getTime().equals(c2.getTime());
    }
}
