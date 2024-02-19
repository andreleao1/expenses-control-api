package br.com.agls.expensescontrolapi.api.dto.in;

import br.com.agls.expensescontrolapi.domain.entity.Account;
import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.enums.PaymentMethod;
import br.com.agls.expensescontrolapi.domain.enums.TransactionStatus;
import br.com.agls.expensescontrolapi.domain.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransactionRequestDTO {

    private final String description;

    @NotNull
    private final String value;

    @NotNull
    private final TransactionCategory category;

    @NotNull
    private final TransactionType type;

    @NotNull
    private final PaymentMethod paymentMethod;

    @NotNull
    private final String transactionDate;

    @NotNull
    private final Account account;
}
