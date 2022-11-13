package br.edu.ifpi.api.transacao.service;

import br.edu.ifpi.api.transacao.data.TransactionRepository;
import br.edu.ifpi.api.transacao.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Collection<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public void deleteAll() {
        transactionRepository.deleteAll();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);

    }
}
