package br.edu.ifpi.api.transacao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    private Long count;
    private Double sum;
    private Double avg;
    private Double min;
    private Double max;
}
