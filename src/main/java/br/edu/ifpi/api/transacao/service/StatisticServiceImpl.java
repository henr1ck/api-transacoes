package br.edu.ifpi.api.transacao.service;

import br.edu.ifpi.api.transacao.domain.Statistic;
import br.edu.ifpi.api.transacao.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService{

    private final TransactionService transactionService;

    @Override
    public Statistic calculateTransactionStatistics() {
        Collection<Transaction> transactions = transactionService.findAll();

        DoubleSummaryStatistics doubleSummaryStatistics = transactions.stream().map(Transaction::getValue)
                .mapToDouble(BigDecimal::doubleValue)
                .summaryStatistics();

        return Statistic.builder()
                .count(doubleSummaryStatistics.getCount())
                .sum(doubleSummaryStatistics.getSum())
                .avg(doubleSummaryStatistics.getAverage())
                .max(doubleSummaryStatistics.getMax())
                .min(doubleSummaryStatistics.getMin())
                .build();

    }
}
