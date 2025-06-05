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

public class DadosElevador {

    private static final String ARQUIVO_ELEVADOR = "bd/elevador.json";

    public static ArrayList<Elevador> listaElevador = carregarElevadores();

    public static void salvarElevadorJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_ELEVADOR)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaElevador, writer);            
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public static ArrayList<Elevador> carregarElevadores() {
        try (FileReader reader = new FileReader(ARQUIVO_ELEVADOR)) {
            Type listaTipo = new TypeToken<ArrayList<Elevador>>(){}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de produtos não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }
  
    public static void atribuirOrdemServico(Scanner scanner) {        
        DadosOrdemServico.listar();
        if(DadosOrdemServico.listaOrdemServico.isEmpty()) return;
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
            case 1 -> tipoElevador = "Elevador geral";
            case 2 -> tipoElevador = "Elevador de alinhamento e balanceamento";
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

    public static void desvincularOrdemServico(Scanner scanner) {
        listar();
        if(listaElevador.isEmpty()) return;
        System.out.print("Selecione o elevador para desvincular a ordem de serviço: ");
        int idElevador = scanner.nextInt();
        scanner.nextLine();

        if(idElevador < 0 || idElevador >= listaElevador.size()) {
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

    public static Elevador buscarElevadorLivrePorTipo(String tipoElevador) {
        for (Elevador elevador : listaElevador) {
            if (elevador.getTipoElevador().equals(tipoElevador) && elevador.getIdOrdemServico() == null) {
                return elevador;
            }
        }
        return null;
    }


    public static void listar(){
        if(listaElevador.isEmpty()){
            System.out.println("Não há elevadores para serem listados!");
        }
        int i = 0;
        for(Elevador e : listaElevador){     
            if (e.getIdOrdemServico() == null) {
                System.out.println("Elevador (" + i + "): " + e.getTipoElevador() + " - Não vinculado a nenhuma ordem de serviço.");
                i++;
                continue;
            }
            OrdemServico os = DadosOrdemServico.buscarId(e.getIdOrdemServico());
            Funcionario f = DadosFuncionario.buscarId(os.getIdFuncionarioResponsavel());
            Veiculo v = DadosVeiculo.buscarId(os.getIdVeiculo());
            System.out.println("Elevador (" + i + "): " + e.getTipoElevador() + "- Funcionario responsável: " + f.getNome()
                + " - Ordem de Serviço: " + e.getIdOrdemServico() + " - Veiculo: " + v.getPlaca() + " " + v.getModelo());
            i++;       
        }
    }

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
