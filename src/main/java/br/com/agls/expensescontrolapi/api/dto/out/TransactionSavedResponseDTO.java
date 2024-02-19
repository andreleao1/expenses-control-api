package br.com.agls.expensescontrolapi.api.dto.out;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class TransactionSavedResponseDTO {

    private final String transactionId;
}
