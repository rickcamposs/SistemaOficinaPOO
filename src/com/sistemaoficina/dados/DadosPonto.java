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

public class DadosPonto {
    private static final String ARQUIVO_PONTO = "bd/ponto.json";

    public static ArrayList<Ponto> listaPontos = carregarPontos();

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

    public static void salvarPontosJson() {
        try (FileWriter writerPonto = new FileWriter(ARQUIVO_PONTO)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaPontos, writerPonto);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

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

    private static ArrayList<Ponto> buscarPontoPorIdEDataEntrada(int id, Date dataEntrada) {
        ArrayList<Ponto> pontosEncontrados = new ArrayList<>();
        for (Ponto p : listaPontos) {
            if (p.getIdFuncionario() == id && mesmaData(p.getDataEntrada(), dataEntrada)) {
                pontosEncontrados.add(p);
            }
        }
        return pontosEncontrados;
    }

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
