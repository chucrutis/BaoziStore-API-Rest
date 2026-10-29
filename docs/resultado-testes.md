# Resultado da validação

Data: 29/10/2026

- Maven integration test: passed; one end-to-end test exercised create, list, retrieve by ID, update, and delete for customer, product, and order.
- Postman-compatible Newman run: 15 requests executed, 15 assertions passed, 0 failed.
- Expected responses observed: POST 201 Created, GET 200 OK, PUT 200 OK, DELETE 204 No Content.
- The run used the H2 in-memory database and the sample customer, product, and order documented in the assignment.

This file records automated execution; it is not a screenshot of the Postman interface. The 12 Postman captures required for the assignment are included in `docs/evidencias-postman/` and in the final report. The top-left interface icon was removed visually from these images, with the requests and responses preserved.

## Execução manual no Postman

Data: 29/10/2026

- 12 requisições obrigatórias executadas contra a API local em `http://localhost:8080`.
- POST de Cliente, Produto e Pedido: 201 Created.
- GET geral e GET por ID dos três recursos: 200 OK.
- DELETE de Pedido, Produto e Cliente, nessa ordem: 204 No Content.
- As capturas do relatório estão em `docs/evidencias-postman/`; o ícone no canto superior esquerdo foi removido visualmente.
