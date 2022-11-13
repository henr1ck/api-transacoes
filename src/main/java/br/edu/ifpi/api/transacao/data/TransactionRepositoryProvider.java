package br.edu.ifpi.api.transacao.data;

import br.edu.ifpi.api.transacao.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Component
public class TransactionRepositoryProvider implements TransactionRepository{
    public Collection<Transaction> transactions = new ArrayList<>();

    @Override
    public Collection<Transaction> findAll() {
        return Collections.unmodifiableCollection(this.transactions);
    }

    @Override
    public void deleteAll() {
        this.transactions.removeAll(findAll());
    }

    @Override
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }
}

