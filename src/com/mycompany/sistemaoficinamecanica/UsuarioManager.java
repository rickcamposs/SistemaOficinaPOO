package com.mycompany.sistemaoficinamecanica;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Classe responsável pela gestão de usuários (funcionários) no sistema.
 * Realiza operações de autenticação e verificação de existência de usuários,
 * além de carregar os dados dos funcionários a partir de um arquivo JSON.
 */
public class UsuarioManager {
    private static final String ARQUIVO_FUNCIONARIOS = "funcionarios.json";
    private ArrayList<Funcionario> funcionarios;

    /**
     * Construtor que inicializa o gerenciador de usuários, carregando a lista de funcionários
     * a partir do arquivo JSON.
     */
    public UsuarioManager() {
        funcionarios = carregarFuncionariosDoJson();
    }

    /**
     * Carrega a lista de funcionários a partir de um arquivo JSON.
     * 
     * @return A lista de funcionários carregada do arquivo JSON.
     */
    private ArrayList<Funcionario> carregarFuncionariosDoJson() {
        try (FileReader reader = new FileReader(ARQUIVO_FUNCIONARIOS)) {
            Type listType = new TypeToken<ArrayList<Funcionario>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionários do JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Realiza a autenticação de um funcionário com base no nome de usuário e senha fornecidos.
     * 
     * @param usuario O nome de usuário do funcionário.
     * @param senha A senha do funcionário.
     * @return O objeto Funcionario autenticado, ou null se as credenciais forem inválidas.
     */
    public Funcionario autenticarFuncionario(String usuario, String senha) {
        for (Funcionario f : funcionarios) {
            if (f.validarCredenciais(usuario, senha)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Verifica se um funcionário com o nome de usuário fornecido já existe no sistema.
     * 
     * @param usuario O nome de usuário do funcionário.
     * @return Verdadeiro se o funcionário existir, falso caso contrário.
     */
    public boolean funcionarioExiste(String usuario) {
        for (Funcionario f : funcionarios) {
            if (f.getUsuario().equals(usuario)) {
                return true;
            }
        }
        return false;
    }
}

