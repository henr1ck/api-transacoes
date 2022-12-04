package br.edu.ifpi.api.transacao.dto;

import br.edu.ifpi.api.transacao.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPostRequestBody {
    @NotNull(message = "transaction value cannot be null")
    @Min(value = 1L, message = "transaction value cannot be zero or negative")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "transaction value must be only two fractional digits")
    private BigDecimal value;

    @NotNull(message = "transaction date cannot be null")
    @Past(message = "transaction date cannot be in the future")
    private OffsetDateTime dateTime;

    public Transaction convertTransactionRequestBodyToTransactionModel(){
        return Transaction.builder()
                .value(this.getValue())
                .dateTime(this.getDateTime())
                .build();
    }
}
