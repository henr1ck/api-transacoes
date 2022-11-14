package br.edu.ifpi.api.transacao.dto;

import br.edu.ifpi.api.transacao.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPostRequestBody {
    @NotNull(message = "transaction value cannot be null")
    @Min(value = 1L, message = "transaction value cannot be zero or negative")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2)
    private BigDecimal value;

    @NotNull(message = "transaction date cannot be null")
    @Future(message = "transaction date cannot be in the past")
    private OffsetDateTime dateTime;

    public Transaction convertTransactionRequestBodyToTransactionModel(){
        return Transaction.builder()
                .value(this.getValue())
                .dateTime(this.getDateTime())
                .build();
    }
}
