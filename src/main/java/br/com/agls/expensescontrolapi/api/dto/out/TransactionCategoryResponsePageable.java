package br.com.agls.expensescontrolapi.api.dto.out;

import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCategoryResponsePageable {

    private List<TransactionCategory> content;
    private Integer page;
    private Integer size;
    private Integer totalElements;
}
