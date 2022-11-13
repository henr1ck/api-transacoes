package br.edu.ifpi.api.transacao.dto;

import br.edu.ifpi.api.transacao.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPostRequestBody {
    private BigDecimal value;
    private OffsetDateTime dateTime;

    public Transaction convertTransactionRequestBodyToTransactionModel(){
        return Transaction.builder()
                .value(this.getValue())
                .dateTime(this.getDateTime())
                .build();
    }
}
