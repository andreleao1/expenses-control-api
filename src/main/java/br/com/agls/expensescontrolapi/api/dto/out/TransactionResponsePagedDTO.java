package br.com.agls.expensescontrolapi.api.dto.out;

import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionResponsePagedDTO {

    @JsonProperty("content")
    private List<Transaction> transactions;
    private Integer page;
    private Integer size;
    private Integer totalElements;
}
