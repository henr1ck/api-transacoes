package br.edu.ifpi.api.transacao.service;

import br.edu.ifpi.api.transacao.data.TransactionRepository;
import br.edu.ifpi.api.transacao.domain.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    private final Transaction testTransaction = Transaction.builder()
            .value(new BigDecimal("99.99"))
            .dateTime(OffsetDateTime.of(LocalDateTime.of(2026, Month.DECEMBER, 2,10,30,10), ZoneOffset.MAX))
            .build();

    @Test
    void findAll_shouldReturnATransactionCollection_whenSuccessful(){
        BDDMockito.given(transactionRepository.findAll()).willReturn(List.of(testTransaction));

        Collection<Transaction> transactions = transactionService.findAll();

        Assertions.assertThat(transactions)
                .isUnmodifiable()
                .singleElement()
                .isEqualTo(testTransaction);
    }

    @Test
    void save_shouldSaveATransaction_whenSuccessful(){
        BDDMockito.given(transactionRepository.save(BDDMockito.any(Transaction.class))).willReturn(testTransaction);

        Transaction transactionSaved = transactionService.save(testTransaction);

        Assertions.assertThat(transactionSaved)
                .extracting(Transaction::getValue, Transaction::getDateTime)
                .contains(transactionSaved.getValue(), transactionSaved.getDateTime());
    }

    @Test
    void deleteAll_shouldDeleteAllTransactions_whenSuccessful(){
        BDDMockito.willDoNothing().given(transactionRepository).deleteAll();

        Assertions.assertThatCode(() -> transactionService.deleteAll())
                .doesNotThrowAnyException();
    }

    @Test
    void findAll_shouldReturnAEmptyTransactionCollection_whenThereAreNotElements(){
        BDDMockito.given(transactionRepository.findAll()).willReturn(List.of());

        Collection<Transaction> transactions = transactionService.findAll();

        Assertions.assertThat(transactions)
                .isUnmodifiable()
                .isEmpty();
    }
}
