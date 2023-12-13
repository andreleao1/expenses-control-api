package br.com.agls.expensescontrolapi.domain.entity;

import br.com.agls.expensescontrolapi.domain.enums.PaymentMethod;
import br.com.agls.expensescontrolapi.domain.enums.TransactionStatus;
import br.com.agls.expensescontrolapi.domain.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

        @Id
        private String id;

        private String description;

        @NotNull(message = "{field.date.required}")
        private String requestId;

        @NotNull(message = "{field.date.required}")
        private BigDecimal value;

        @ManyToOne
        private TransactionCategory category;

        @NotNull(message = "{field.date.required}")
        private TransactionType type;

        @NotNull(message = "{field.date.required}")
        private PaymentMethod paymentMethod;

        @NotNull(message = "{field.date.required}")
        private TransactionStatus status;

        @NotNull(message = "{field.date.required}")
        private LocalDateTime createdAt;

        @NotNull(message = "{field.date.required}")
        private LocalDateTime updatedAt;
}
