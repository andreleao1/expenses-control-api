package br.com.agls.expensescontrolapi.util;

import br.com.agls.expensescontrolapi.api.dto.in.TransactionRequestDTO;
import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import br.com.agls.expensescontrolapi.domain.enums.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class TransactionBuilder {

    private TransactionBuilder() {
    }

    public static Transaction execute(TransactionRequestDTO transactionRequestDTO, RequestParams requestParams) {
        return Transaction.builder()
                .description(transactionRequestDTO.getDescription())
                .value(BigDecimal.valueOf(Double.parseDouble(transactionRequestDTO.getValue())))
                .category(transactionRequestDTO.getCategory())
                .type(transactionRequestDTO.getType())
                .paymentMethod(transactionRequestDTO.getPaymentMethod())
                .status(getTransactionStatus(LocalDate.parse(transactionRequestDTO.getTransactionDate())))
                .requestId(Objects.nonNull(requestParams.getRequestId()) ? requestParams.getRequestId() : UUID.randomUUID().toString())
                .userId(requestParams.getUserId())
                .account(transactionRequestDTO.getAccount())
                .transactionDate(LocalDate.parse(transactionRequestDTO.getTransactionDate()))
                .updatedAt(LocalDate.now())
                .createdAt(LocalDate.now())
                .build();
    }

    private static TransactionStatus getTransactionStatus(LocalDate transactionDate) {
        return transactionDate.isAfter(LocalDate.now()) ? TransactionStatus.SCHEDULED : TransactionStatus.MADE;
    }

    @Data
    @Builder
    public static class RequestParams {
        public String requestId;
        public String userId;
    }
}
