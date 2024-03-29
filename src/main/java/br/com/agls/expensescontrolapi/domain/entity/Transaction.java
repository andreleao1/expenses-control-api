package br.com.agls.expensescontrolapi.domain.entity;

import br.com.agls.expensescontrolapi.domain.enums.PaymentMethod;
import br.com.agls.expensescontrolapi.domain.enums.TransactionStatus;
import br.com.agls.expensescontrolapi.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        private String description;

        @NotNull(message = "{field.requestId.required}")
        private String requestId;

        @NotNull(message = "{field.userId.required}")
        private String userId;

        @NotNull(message = "{field.value.required}")
        private BigDecimal value;

        @ManyToOne
        private TransactionCategory category;

        @NotNull(message = "{field.type.required}")
        private TransactionType type;

        @NotNull(message = "{field.paymentMethod.required}")
        private PaymentMethod paymentMethod;

        @NotNull(message = "{field.status.required}")
        private TransactionStatus status;

        @JsonBackReference
        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        private Account account;

        @NotNull(message = "{field.transactionDate.required}")
        private LocalDate transactionDate;

        @NotNull(message = "{field.createdAt.required}")
        private LocalDate createdAt;

        @NotNull(message = "{field.updatedAt.required}")
        private LocalDate updatedAt;
}
