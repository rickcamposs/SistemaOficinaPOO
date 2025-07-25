# Sistema de Gestão de Oficina – Milho Verde

Projeto acadêmico de Programação Orientada a Objetos (POO)
**Em desenvolvimento – versão atual cobre todos os principais requisitos da disciplina.**

## Descrição

Este sistema foi desenvolvido para gerenciar de forma eficiente todos os processos operacionais de uma oficina mecânica de pequeno/médio porte, situada em Milho Verde.
O software permite automatizar e organizar tarefas rotineiras como controle de clientes, veículos, estoque, vendas, serviços, funcionários e financeiro, integrando funcionalidades essenciais em uma solução de fácil uso, baseada em linha de comando.

## Principais Funcionalidades

* **Gerenciamento de Serviços**: Controle completo das ordens de serviço com status (recebido, em manutenção, direcionamento, entregue, cancelado e finalizado).
* **Agendamento Inteligente**: Cadastro, edição e controle de agendamentos, com verificação de disponibilidade e status.
* **Cadastro de Clientes e Veículos**: Inclusão, edição e busca de clientes com CPF pseudo-anonimizado; associação de múltiplos veículos a cada cliente.
* **Gestão de Funcionários**: Cadastro, edição, autenticação, controle de ponto e permissões por cargo (incluindo proprietário e administrador).
* **Controle de Estoque**: Gerenciamento de produtos/peças, com registro de vendas, saídas para serviços e atualização automática do estoque.
* **Notas Fiscais**: Emissão e impressão de notas fiscais detalhadas, vinculadas à ordem de serviço e produtos utilizados.
* **Despesas e Financeiro**: Cadastro de despesas por categoria, geração de balanço mensal e relatórios de vendas/serviços.
* **Relatórios e Extratos**: Relatórios de vendas, balanço financeiro, extratos por cliente, controle de produtos utilizados e estoque.
* **Controle de Acesso**: Autenticação obrigatória de funcionário ao iniciar o sistema; acesso a funções restritas conforme o cargo.
* **Gestão de Elevadores**: Associação de ordens de serviço a elevadores (incluindo tipo: geral ou alinhamento).

## Requisitos e Critérios Atendidos

* Diagrama de casos de uso e fluxos de eventos (documentação anexada)
* Diagrama de classes completo (com herança, polimorfismo, sobrescrita, uso de static e interface Comparator)
* Encapsulamento rigoroso dos dados, construtores e métodos
* Persistência de dados em arquivos .json utilizando Gson
* Interface de menus com validação de entrada e mensagens ao usuário
* JavaDoc completo em todas as classes, métodos e atributos
* Geração de extratos e relatórios de acordo com o usuário
* Modularização com separação clara entre entidades, dados e lógica de controle

## Estrutura do Projeto

```
com.sistemaoficina/
│
├── dto/         Entidades: Cliente, Veículo, Produto, Funcionario, etc.
├── dados/       Persistência: classes de acesso e controle a arquivos JSON.
├── enums/       Enumerações: StatusServico, Combustivel, CategoriaDespesa.
├── comparator/  Comparadores customizados (ComparatorCliente).
├── main/        Execução principal e interface de menus.
└── (bd/)        Arquivos .json persistidos localmente.
```

## Como Usar

1. Compile o projeto com sua IDE preferida ou usando javac.
2. Execute a classe principal:
   `com.sistemaoficina.main.SistemaOficina`
3. Faça login como funcionário.
4. O acesso a funcionalidades avançadas depende do cargo.
5. Navegue pelos menus para gerenciar clientes, veículos, serviços, funcionários, financeiro, etc.

## Dependências
 
* [Gson](https://github.com/google/gson) (serialização e leitura dos arquivos JSON)
* Java SE 8 ou superior

## Observações

* As senhas dos funcionários são armazenadas em texto puro (apenas para fins acadêmicos).
* O projeto pode ser estendido facilmente para interface gráfica (JavaFX/Swing) ou integração web.
* Código amplamente documentado via JavaDoc para facilitar entendimento e manutenção.

---
