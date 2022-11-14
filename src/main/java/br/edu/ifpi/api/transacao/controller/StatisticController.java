package br.edu.ifpi.api.transacao.controller;

import br.edu.ifpi.api.transacao.domain.Statistic;
import br.edu.ifpi.api.transacao.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping
    public ResponseEntity<Statistic> calculateTransactionStatistics(){
        return ResponseEntity.ok(statisticService.calculateTransactionStatistics());
    }
}
