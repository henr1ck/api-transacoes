package br.edu.ifpi.api.transacao.service;


import br.edu.ifpi.api.transacao.domain.Transaction;

import java.util.Collection;

public interface TransactionService {
    Collection<Transaction> findAll();
    void deleteAll();
    Transaction save(Transaction transacao);
}
