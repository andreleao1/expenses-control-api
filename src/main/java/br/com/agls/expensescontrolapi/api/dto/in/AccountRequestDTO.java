package br.com.agls.expensescontrolapi.api.dto.in;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String userId;

    @NotBlank
    private String icon;
}
