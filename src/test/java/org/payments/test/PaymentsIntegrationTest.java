package org.payments.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.payments.PaymentProcessor;
import org.payments.model.Payee;
import org.payments.model.Payer;
import org.payments.model.Payment;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PaymentProcessor.class)
@EnableAutoConfiguration
@Testcontainers
public class PaymentsIntegrationTest {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres");

    @Container
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka-native:latest")
            .asCompatibleSubstituteFor("apache/kafka"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        // Maps the container's random port to the YAML property
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @LocalServerPort
    private int port;


    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @AfterEach
    public void tearDown() {
        RestAssured.reset();
        postgreSQLContainer.stop();
        kafka.stop();
    }

    @Test
    void shouldPersistPayment() {
        var paymentId = UUID.randomUUID();
        var accountId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var payment = Payment.builder()
                .id(paymentId)
                .payee(Payee.builder()
                        .accountId(accountId)
                        .userId(userId)
                        .build())
                .payer(Payer.builder()
                        .name("full name")
                        .build())
                .amount(new BigDecimal("25.5"))
                .build();

        given().log().all()
                .contentType(ContentType.JSON)
                .body(payment)
                .when()
                .post("/payment")
                .then()
                .log().all()
                .statusCode(201);
    }
}
