Sistema de Vendas & Gestão de Estoque

Este é um sistema Full Stack desenvolvido para gerenciar operações comerciais de pequeno e médio porte. O projeto foca no controle rigoroso de inventário, permitindo que a empresa gerencie desde o cadastro básico de parceiros até a movimentação financeira de entradas e saídas (vendas).

🚀 Principais Funcionalidades
🛒 Módulo de Vendas (PDV): Interface para realização de vendas com seleção de cliente, produtos e vendedor. O sistema realiza a baixa automática no estoque e calcula o valor total em tempo real.

📦 Gestão de Estoque (Entradas): Registro de compras e abastecimento. Ao registrar uma entrada de mercadoria, o sistema soma automaticamente as quantidades ao estoque atual.

👥 Cadastros Completos (CRUDs): Gestão total de Clientes, Fornecedores, Funcionários, Produtos, Cidades e Estados.

🔄 Inteligência de Estorno: Sistema preparado para cancelamentos; ao excluir uma venda ou entrada, o estoque é estornado automaticamente para garantir a integridade dos dados.

📊 Painel Administrativo: Home interativa com atalhos rápidos para as operações mais importantes do dia a dia.

🛠️ Tecnologias Utilizadas
Backend: Java 17 com Spring Boot 3.

Persistência: Spring Data JPA com Hibernate.

Banco de Dados: MySQL.

Frontend: Thymeleaf, HTML5, CSS3 e Bootstrap 5 (Layout responsivo).

Ícones: FontAwesome.

Controle de Versão: Git & GitHub.

💡 Como rodar o projeto?
Clone o repositório.

Configure o banco de dados MySQL no arquivo application.properties.

Execute a classe SistemaApplication.java.

Acesse localhost:8080/administrativo.

📝 Autor
Desenvolvido por Guilherme Rodrigues.
