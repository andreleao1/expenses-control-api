package br.com.agls.expensescontrolapi.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponseDTO {

    private String id;

    private String userId;

    private String name;

    private String icon;

    private String balance;

    private Boolean archived;
}
