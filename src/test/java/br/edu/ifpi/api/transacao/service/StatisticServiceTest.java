package br.edu.ifpi.api.transacao.service;

import br.edu.ifpi.api.transacao.domain.Statistic;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTest {

    @InjectMocks
    private StatisticServiceImpl statisticService;

    @Mock
    private TransactionServiceImpl transactionService;

    private final Transaction testTransaction1 = Transaction.builder()
            .value(new BigDecimal("99.99"))
            .dateTime(OffsetDateTime.of(LocalDateTime.of(2026, Month.DECEMBER, 2,10,30,10), ZoneOffset.MAX))
            .build();

    private final Transaction testTransaction2 = Transaction.builder()
            .value(new BigDecimal("49.99"))
            .dateTime(OffsetDateTime.of(LocalDateTime.of(2026, Month.DECEMBER, 2,10,30,10), ZoneOffset.MAX))
            .build();

    @Test
    void calculateTransactionStatistics_shouldReturnTransactionStatistics_whenSuccessful(){
        BDDMockito.given(transactionService.findAll()).willReturn(List.of(testTransaction1, testTransaction2));

        Statistic statistic = statisticService.calculateTransactionStatistics();

        Assertions.assertThat(statistic.getCount()).isEqualTo(2L);
        Assertions.assertThat(statistic.getSum()).isEqualTo(149.98);
        Assertions.assertThat(statistic.getAvg()).isEqualTo(74.99);
        Assertions.assertThat(statistic.getMax()).isEqualTo(99.99);
        Assertions.assertThat(statistic.getMin()).isEqualTo(49.99);

    }
}
