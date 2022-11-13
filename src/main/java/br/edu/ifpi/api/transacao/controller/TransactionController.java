package br.edu.ifpi.api.transacao.controller;

import br.edu.ifpi.api.transacao.domain.Transaction;
import br.edu.ifpi.api.transacao.dto.TransactionPostRequestBody;
import br.edu.ifpi.api.transacao.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> save(HttpServletRequest request, @RequestBody TransactionPostRequestBody transactionDto){
        Transaction transaction = transactionDto.convertTransactionRequestBodyToTransactionModel();

        return ResponseEntity.created(URI.create(request.getRequestURI())).body(transactionService.save(transaction));
    }

    @GetMapping
    public ResponseEntity<Collection<Transaction>> findAll(){
        return ResponseEntity.ok().body(transactionService.findAll());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        transactionService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
