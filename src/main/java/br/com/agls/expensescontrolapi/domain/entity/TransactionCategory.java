package br.com.agls.expensescontrolapi.domain.entity;

import br.com.agls.expensescontrolapi.domain.enums.TransactionCategoryStatus;
import br.com.agls.expensescontrolapi.domain.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{field.name.required}")
    private String name;

    private String description;

    @NotNull(message = "{field.transactionType.required}")
    private TransactionType transactionType;

    private TransactionCategoryStatus status;

    private String icon;

    @Builder.Default
    private String color = "#000000";
}
