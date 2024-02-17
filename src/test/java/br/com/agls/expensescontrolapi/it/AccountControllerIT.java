package br.com.agls.expensescontrolapi.it;

import br.com.agls.expensescontrolapi.api.dto.in.AccountRequestDTO;
import br.com.agls.expensescontrolapi.domain.entity.Account;
import br.com.agls.expensescontrolapi.util.AccountUtils;
import com.google.gson.Gson;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static br.com.agls.expensescontrolapi.it.utils.AccountUtils.generateAccounts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AccountControllerIT.Initializer.class})
@Testcontainers
public class AccountControllerIT {

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

    private Account account;

    @BeforeEach
    public void setup() {
        List<Account> accounts = generateAccounts();

        account = accounts.get(0);

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        jdbcTemplate.execute("DELETE FROM account");
        accounts.forEach(account -> {
            jdbcTemplate.execute("INSERT INTO account (account_id, user_id, name, icon, balance, archived, created_at, updated_at) VALUES ('" + account.getId() + "', '" + account.getUserId() + "', '" + account.getName() + "', '" + account.getIcon() + "', '" + account.getBalance() + "', " + account.getArchived() + ", '" + account.getCreatedAt() + "', '" + account.getUpdatedAt() + "')");
        });
    }


    @Test
    @DisplayName("Save valid account")
    void whenPostAccountShouldReturn201() throws Exception {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .userId(UUID.randomUUID().toString())
                .name("Account 4")
                .icon("Icon 4")
                .build();

        String accountJson = new Gson().toJson(accountRequestDTO);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Archive account")
    void whenPutAccountArchiveShouldReturn200() throws Exception {
        mockMvc.perform(put("/accounts/" + account.getId() + "/user/" + account.getUserId() +"/archive")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Unarchive account")
    void whenPutAccountUnarchiveShouldReturn200() throws Exception {
        mockMvc.perform(put("/accounts/" + account.getId() + "/user/" + account.getUserId() +"/unarchive")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Find accounts by user id")
    void whenGetAccountsByUserIdShouldReturn200() throws Exception {
        var response = mockMvc.perform(get("/accounts/user/" + account.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(new Gson().toJson(AccountUtils.parseToAccountResponseDTO(Collections.singletonList(account))), response.getContentAsString());
    }

    @Test
    @DisplayName("Delete account")
    void whenDeleteAccountShouldReturn200() throws Exception {
        jdbcTemplate.update("UPDATE account SET balance = ?, archived = ? WHERE account_id = ?", BigDecimal.ZERO.toString(), true, account.getId());

        mockMvc.perform(delete("/accounts/" + account.getId() + "/user/" + account.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Error deleting account because it isn't archived")
    void whenDeleteAccountThatIsNotArchivedShouldReturn400() throws Exception {
        mockMvc.perform(delete("/accounts/" + account.getId() + "/user/" + account.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Error deleting account because it has balance")
    void whenDeleteAccountWithBalanceDifferentOfZeroShouldReturn400() throws Exception {
        jdbcTemplate.update("UPDATE account SET balance = ? WHERE account_id = ?", BigDecimal.ONE.toString(), account.getId());

        mockMvc.perform(delete("/accounts/" + account.getId() + "/user/" + account.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
