package com.victorvillar.baozistore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ApiIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void executesCrudForCustomerProductAndOrder() {
        ResponseEntity<Map> customerCreated = restTemplate.postForEntity("/api/clientes",
                Map.of("nome", "Victor Martins Villar RU 5227284", "clienteDesde", "2026-10-29"), Map.class);
        assertEquals(HttpStatus.CREATED, customerCreated.getStatusCode());
        Long customerId = idOf(customerCreated);
        assertEquals(HttpStatus.OK, restTemplate.getForEntity("/api/clientes", Object.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity("/api/clientes/" + customerId, Object.class).getStatusCode());

        ResponseEntity<Map> productCreated = restTemplate.postForEntity("/api/produtos",
                Map.of("nome", "Pão chinês de carne", "preco", new BigDecimal("8.50"), "estoque", true), Map.class);
        assertEquals(HttpStatus.CREATED, productCreated.getStatusCode());
        Long productId = idOf(productCreated);
        assertEquals(HttpStatus.OK, restTemplate.getForEntity("/api/produtos", Object.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity("/api/produtos/" + productId, Object.class).getStatusCode());

        ResponseEntity<Map> orderCreated = restTemplate.postForEntity("/api/pedidos",
                Map.of("clienteId", customerId, "produtoId", productId, "quantidade", 3), Map.class);
        assertEquals(HttpStatus.CREATED, orderCreated.getStatusCode());
        Long orderId = idOf(orderCreated);
        assertNotNull(orderCreated.getBody().get("clienteNome"));
        assertEquals(HttpStatus.OK, restTemplate.getForEntity("/api/pedidos", Object.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity("/api/pedidos/" + orderId, Object.class).getStatusCode());

        restTemplate.delete("/api/pedidos/" + orderId);
        restTemplate.delete("/api/produtos/" + productId);
        restTemplate.delete("/api/clientes/" + customerId);
        assertEquals(HttpStatus.NOT_FOUND, restTemplate.getForEntity("/api/pedidos/" + orderId, Object.class).getStatusCode());
    }

    private Long idOf(ResponseEntity<Map> response) {
        assertNotNull(response.getBody());
        return ((Number) response.getBody().get("id")).longValue();
    }
}
