package br.com.agls.expensescontrolapi.api.dto.out;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class TransactionResponseDTO {

    private final String transactionId;
}
