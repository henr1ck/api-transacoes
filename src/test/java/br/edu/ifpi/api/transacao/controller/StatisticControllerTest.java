package br.edu.ifpi.api.transacao.controller;

import br.edu.ifpi.api.transacao.domain.Statistic;
import br.edu.ifpi.api.transacao.service.StatisticServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = StatisticController.class)
public class StatisticControllerTest {

    @MockBean
    private StatisticServiceImpl statisticService;
    @Autowired
    private MockMvc mockMvc;

    private final Statistic statisticTest = Statistic.builder()
            .count(1L)
            .sum(149.98)
            .avg(74.99)
            .max(99.99)
            .min(49.99)
            .build();

    @Test
    void calculateTransactionStatistic_shouldReturnTransactionStatistics_whenSuccessful() throws Exception {
        BDDMockito.given(statisticService.calculateTransactionStatistics()).willReturn(statisticTest);

        MockHttpServletRequestBuilder request = post(URI.create("/api/statistics"));

        Statistic statistic = statisticService.calculateTransactionStatistics();

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(statistic.getCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sum").value(statistic.getSum()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avg").value(statistic.getAvg()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.max").value(statistic.getMax()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").value(statistic.getMin()));
    }
}
