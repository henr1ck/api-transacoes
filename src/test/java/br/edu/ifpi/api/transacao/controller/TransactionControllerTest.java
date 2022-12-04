package br.edu.ifpi.api.transacao.controller;

import br.edu.ifpi.api.transacao.domain.Transaction;
import br.edu.ifpi.api.transacao.dto.TransactionPostRequestBody;
import br.edu.ifpi.api.transacao.service.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @MockBean
    private TransactionServiceImpl transactionService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/transaction";

    private final Transaction testTransaction = Transaction.builder()
            .value(new BigDecimal("99.99"))
            .dateTime(OffsetDateTime.of(LocalDateTime.of(2020, Month.DECEMBER, 2,10,30,10), ZoneOffset.MAX))
            .build();

    @Test
    void save_shouldReturnASavedTransaction_whenSuccessful() throws Exception {
        BDDMockito.given(transactionService.save(BDDMockito.any(Transaction.class))).willReturn(testTransaction);

        MockHttpServletRequestBuilder request = post(BASE_URL)
                .content(objectMapper.writeValueAsString(TransactionPostRequestBody.builder()
                        .value(testTransaction.getValue())
                        .dateTime(testTransaction.getDateTime())
                        .build()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value").value(testTransaction.getValue().toPlainString()))
                .andExpect(jsonPath("$.dateTime").value(testTransaction.getDateTime().toString()));
    }

    @Test
    void delete_shouldReturnNoContentStatusCode_whenSuccessful() throws Exception {
        BDDMockito.willDoNothing().given(transactionService).deleteAll();

        MockHttpServletRequestBuilder request = delete(BASE_URL);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void findAll_shouldReturnATransactionCollection_whenSuccessful() throws Exception {
        BDDMockito.given(transactionService.findAll()).willReturn(List.of(testTransaction));

        MockHttpServletRequestBuilder request = get(BASE_URL);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].value").value(testTransaction.getValue().toPlainString()))
                .andExpect(jsonPath("$[0].dateTime").value(testTransaction.getDateTime().toString()));;
    }

    @Test
    void save_shouldThrowAMethodArgumentNotValidExceptionDescriptor_whenDateIsInTheFuture() throws Exception {
        MockHttpServletRequestBuilder request = post(BASE_URL)
                .content(objectMapper.writeValueAsString(TransactionPostRequestBody.builder()
                        .value(testTransaction.getValue())
                        .dateTime(testTransaction.getDateTime().plusYears(1000L))
                        .build()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.exception").value(MethodArgumentNotValidException.class.getSimpleName()))
                .andExpect(jsonPath("$.fields").value("dateTime"))
                .andExpect(jsonPath("$.fieldsMessage").value("transaction date cannot be in the future"));
    }

    @Test
    void save_shouldThrowAMethodArgumentNotValidExceptionDescriptor_whenValueIsNegative() throws Exception {
        MockHttpServletRequestBuilder request = post(BASE_URL)
                .content(objectMapper.writeValueAsString(TransactionPostRequestBody.builder()
                        .value(testTransaction.getValue().multiply(BigDecimal.ONE.negate()))
                        .dateTime(testTransaction.getDateTime())
                        .build()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.exception").value(MethodArgumentNotValidException.class.getSimpleName()))
                .andExpect(jsonPath("$.fields").value("value"))
                .andExpect(jsonPath("$.fieldsMessage").value("transaction value cannot be zero or negative"));;
    }

    @Test
    void save_shouldThrowAMethodArgumentNotValidExceptionDescriptor_whenDateIsNull() throws Exception {
        MockHttpServletRequestBuilder request = post(BASE_URL)
                .content(objectMapper.writeValueAsString(TransactionPostRequestBody.builder()
                        .value(testTransaction.getValue())
                        .dateTime(null)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.exception").value(MethodArgumentNotValidException.class.getSimpleName()))
                .andExpect(jsonPath("$.fields").value("dateTime"))
                .andExpect(jsonPath("$.fieldsMessage").value("transaction date cannot be null"));
    }

    @Test
    void save_shouldThrowAMethodArgumentNotValidExceptionDescriptor_whenValueIsNull() throws Exception {
        MockHttpServletRequestBuilder request = post(BASE_URL)
                .content(objectMapper.writeValueAsString(TransactionPostRequestBody.builder()
                        .value(null)
                        .dateTime(testTransaction.getDateTime())
                        .build()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.exception").value(MethodArgumentNotValidException.class.getSimpleName()))
                .andExpect(jsonPath("$.fields").value("value"))
                .andExpect(jsonPath("$.fieldsMessage").value("transaction value cannot be null"));
    }

    @Test
    void save_shouldThrowAMethodArgumentNotValidExceptionDescriptor_whenThereAreNoTwoFractionalDigits() throws Exception {
        MockHttpServletRequestBuilder request = post(BASE_URL)
                .content(objectMapper.writeValueAsString(TransactionPostRequestBody.builder()
                        .value(new BigDecimal("99.998"))
                        .dateTime(testTransaction.getDateTime())
                        .build()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.exception").value(MethodArgumentNotValidException.class.getSimpleName()))
                .andExpect(jsonPath("$.fields").value("value"))
                .andExpect(jsonPath("$.fieldsMessage").value("transaction value must be only two fractional digits"));
    }

    @Test
    void save_shouldReturnUnsupportedMediaTypeStatusCode_whenContentTypeIsNotApplicationJson() throws Exception {
        MockHttpServletRequestBuilder request = post(BASE_URL)
                .content(objectMapper.writeValueAsString(TransactionPostRequestBody.builder()
                        .value(testTransaction.getValue())
                        .dateTime(testTransaction.getDateTime())
                        .build()))
                .contentType(MediaType.TEXT_HTML);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }
}
