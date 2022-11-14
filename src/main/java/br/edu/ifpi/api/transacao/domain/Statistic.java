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
    @Builder.Default
    private Double min = 0.0;
    @Builder.Default
    private Double max = 0.0;
}
