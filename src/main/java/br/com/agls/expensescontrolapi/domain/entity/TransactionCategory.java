package br.com.agls.expensescontrolapi.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionCategory {

    @Id
    private Long id;

    @NotNull(message = "{field.name.required}")
    private String name;

    private String description;

    private String icon;

    @Builder.Default
    private String color = "#000000";
}
