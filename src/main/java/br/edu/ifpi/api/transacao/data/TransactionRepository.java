package br.edu.ifpi.api.transacao.data;

import br.edu.ifpi.api.transacao.domain.Transaction;

import java.util.Collection;

public interface TransactionRepository {
    Collection<Transaction> findAll();
    void deleteAll();
    Transaction save(Transaction transacao);
}
