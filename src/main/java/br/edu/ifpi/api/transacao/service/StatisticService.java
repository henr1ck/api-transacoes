package br.edu.ifpi.api.transacao.service;

import br.edu.ifpi.api.transacao.domain.Statistic;

public interface StatisticService {
    Statistic calculateTransactionStatistics();
}
