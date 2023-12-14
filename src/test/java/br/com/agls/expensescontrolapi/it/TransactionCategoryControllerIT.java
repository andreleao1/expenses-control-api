package br.com.agls.expensescontrolapi.it;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {TransactionCategoryControllerIT.Initializer.class})
@Testcontainers
public class TransactionCategoryControllerIT {


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("integration-tests-db")
            .withUsername("username")
            .withPassword("password");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    configurableApplicationContext,
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            );
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        jdbcTemplate.execute("DELETE FROM transaction_category");
        jdbcTemplate.execute("INSERT INTO transaction_category (name, description, status, icon, color) VALUES ('Category 1', 'Description 1', 0,'Icon 1', 'Color 1')");
        jdbcTemplate.execute("INSERT INTO transaction_category (name, description, status, icon, color) VALUES ('Category 2', 'Description 2', 0,'Icon 2', 'Color 2')");
        jdbcTemplate.execute("INSERT INTO transaction_category (name, description, status, icon, color) VALUES ('Category 3', 'Description 3', 0,'Icon 3', 'Color 3')");
    }

    @Test
    void whenPostTransactionCategory_thenReturns201() throws Exception {
        String transactionCategoryJson = "{\"name\":\"Category 4\",\"description\":\"Description 4\",\"status\":\"ACTIVE\",\"icon\":\"Icon 1\",\"color\":\"Color 1\"}";

        mockMvc.perform(post("/transaction-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionCategoryJson))
                .andExpect(status().isCreated());
    }

    @Test
    void whenPostTransactionCategoryGivenInvalidStatus_thenReturns400() throws Exception {
        String transactionCategoryJson = "{\"name\":\"Category 4\",\"description\":\"Description 4\",\"status\":\"FALSE\",\"icon\":\"Icon 1\",\"color\":\"Color 1\"}";

        mockMvc.perform(post("/transaction-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionCategoryJson))
                .andExpect(status().isBadRequest());
    }
}