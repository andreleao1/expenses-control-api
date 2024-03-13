package br.com.agls.expensescontrolapi.api.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticatedUser {
    private String iss;
    private String userId;
    private String role;
    private Double exp;
}
