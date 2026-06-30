# 🚚 Sistema de Gestão e Monitoramento de Pátio Logístico

## 🧑‍💻 Autor: Pablo Gomes Nogueira, Matheus Duarte Rosa e Pedro Gomes Nogueira
**Status do Projeto:** Homologado e Aprovado para Produção 🚀

---

## 🛠️ 1. Tecnologias Utilizadas & Infraestrutura
*   **Linguagem:** Java (JDK 17) com foco em Programação Orientada a Objetos (POO).
*   **Banco de Dados:** MySQL / MariaDB (Porta padrão `3306`).
*   **Persistência:** Conexão nativa via JDBC (`DriverManager` e `PreparedStatement` para segurança contra SQL Injection).
*   **Gestão de Tarefas:** Quadro Kanban estruturado no Trello para controle de ciclo de vida do software (SDLC).

---

## 📐 3. Modelagem de Dados & Arquitetura (O que falar sobre o Banco)
O sistema conta com 7 entidades totalmente integradas no MySQL sob a base `logistica`:

1.  **`cliente`:** Armazena dados de faturamento e diferencia parceiros comerciais entre Remetentes e Destinatários.
2.  **`motorista`:** Armazena dados dos condutores e validações de categorias de CNH.
3.  **`produto`:** Controla dimensões físicas (largura, altura, empilhamento máximo) das mercadorias dos clientes.
4.  **`pedido`:** Controla as ordens de serviço e o status de faturamento.
5.  **`itens_pedido`:** Tabela associativa que controla as quantidades e pesos parciais de produtos por pedido.
6.  **`veiculo`:** Gerencia frotas, registrando a capacidade máxima de peso e cubagem volumétrica de cada carreta.
7.  **`manifestos_carga`:** O coração do pátio logístico. Realiza a amarração final unificando a data de partida, volume total ocupado,
8.  peso carregado, o veículo escalado, o motorista e o pedido correspondente.

---

## 🚀 4. Demonstração dos Módulos (O fluxo do sistema)
> *“A aplicação se comunica através de um Menu Principal via terminal de texto de fácil operação, estruturado em blocos CRUD:”*

*   **Painel Administrativo:** Permite o controle isolado de Clientes, Motoristas, Produtos e Veículos através de inserções tratadas.
*   **Inteligência de Cubagem:** O sistema foi projetado para ler as dimensões físicas e pesos das cargas para garantir que o manifesto
    criado respeite rigorosamente os limites de capacidade máxima do veículo associado, prevenindo sobrecarga.

---

## 🛡️ 5. Engenharia de Qualidade & Gestão de Bugs (Ponto alto da apresentação)
> *“Durante as etapas de testes de caixa-branca e homologação da versão v1.0.0, nossa equipe de QA identificou e tratou de forma preventiva
>  duas falhas críticas de execução antes do deploy final:”*

1.  **Bug Sintático na classe `Carga.java` (Resolvido):** Foi detectada uma corrupção de string SQL (`wwwwww` colado à cláusula `WHERE`) que abortava
     a operação ao tentar atualizar os dados completos de uma carga. A query foi sanitizada e corrigida.
3.  **Bug de Tipagem na classe `Pedido.java` (Resolvido):** O console utilizava um leitor de ponto flutuante (`nextDouble()`) para ler chaves primárias inteiras, gerando exceções
     de incompatibilidade (`InputMismatchException`). A leitura foi ajustada para inteiros (`nextInt()`), garantindo estabilidade total.

---

## 📋 6. Governança e Processos de TI (Os Entregáveis da Banca)
Para atender aos padrões corporativos de governança, o repositório está amarrado aos seguintes documentos de engenharia:
*   **Quadro Trello:** Fluxo visual do projeto (Backlog, Em Andamento, Em Teste, Reprovado, Concluído).
*   **Casos de Teste (CT):** Roteiros de validação para atualizações cadastrais e integridade de tipos.
*   **Política de Backup:** Estratégia de resiliência baseada na regra 3-2-1, utilizando automação via rotinas agendadas (CRON) e validações estruturais semanais.
*   **Gestão de Mudanças (GMUD):** Plano formal de implantação (Deploy) e plano de contingência (Rollback) documentando o histórico de alterações da versão.
*   **Release Notes:** Documento oficial de aceite técnico com foco em negócios e valor comercial do software entregue.

---

## 🏁 7. Conclusão (Fechamento da apresentação)
> *“Com a aplicação do Git Flow para o isolamento de ramificações de correção e novas features, entregamos hoje um sistema robusto, escalável, com banco de dados normalizado
   e totalmente protegido contra falhas críticas de execução. O sistema está homologado e pronto para produção. Fico à disposição para dúvidas da banca. Obrigado.”*
