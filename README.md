# Baozi Store API

Projeto de atividade prática para a disciplina Desenvolvimento Web Back-End. A API REST cadastra clientes, produtos e pedidos usando Spring MVC, Spring Data JPA e H2.

Repositório público: https://github.com/chucrutis/BaoziStore-API-Rest

## Requisitos

- Java 17 ou superior
- Apache Maven 3.9 ou superior
- Postman, para importar e executar a coleção incluída

## Executar

No diretório do projeto, rode:

mvn spring-boot:run

A API fica disponível em http://localhost:8080. O banco H2 é criado em memória quando a aplicação inicia e é reiniciado quando ela é encerrada.

## Testes automatizados

O teste de integração Maven passou. A coleção também passou no Newman: 15 requisições e 15 verificações, sem falhas. O resumo está em `docs/resultado-testes.md`.

## Verificar no Postman

1. Importe postman/BaoziStore.postman_collection.json.
2. Importe postman/BaoziStore.postman_environment.json e selecione o ambiente Baozi Store Local.
3. Inicie a aplicação e execute as requisições da coleção na ordem apresentada.
4. A coleção guarda os IDs retornados pelos POST e os usa nos GET, PUT e DELETE seguintes.
5. As 12 capturas solicitadas estão em `docs/evidencias-postman/` e também no relatório. Os IDs podem variar entre execuções.

## Evidências da atividade

O projeto inclui 12 capturas do Postman: POST, GET geral, GET por ID e DELETE para Cliente, Produto e Pedido. O ícone de interface no canto superior esquerdo foi removido visualmente das imagens, sem alterar requisições ou respostas. O relatório completo em PDF e DOCX está no pacote de entrega.

## Endpoints

- Clientes: /api/clientes e /api/clientes/{id}
- Produtos: /api/produtos e /api/produtos/{id}
- Pedidos: /api/pedidos e /api/pedidos/{id}

Cada recurso oferece POST, GET geral, GET por ID, PUT e DELETE. As exclusões de cliente ou produto retornam 409 enquanto houver pedidos vinculados; exclua o pedido primeiro.

## Banco de dados

- Tipo: H2 em memória
- Usuário: sa
- Senha: em branco
- Console H2: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:baozi

## Modelo da atividade

- Cliente: id, nome, clienteDesde
- Produto: id, nome, preco, estoque (indicador booleano de disponibilidade)
- Pedido: id, clienteId, produtoId, quantidade

O pedido armazena relações JPA com cliente e produto. A resposta JSON apresenta os IDs e nomes relacionados para facilitar a leitura.
