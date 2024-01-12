package com.rhtmdwh.batchcampus.application.dormant;

import com.rhtmdwh.batchcampus.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
class PreDormantBatchItemProcessorTest {

    private PreDormantBatchItemProcessor preDormantBatchItemProcessor;

    @BeforeEach
    void setup(){
        preDormantBatchItemProcessor = new PreDormantBatchItemProcessor();
    }

    @Test
    @DisplayName("로그인 날짜가 오늘부터 358일 전이면 customer 를 반환애햐한다.")
    void test1(){
        // given
        Customer customer = new Customer("minsoo", "minsoo@naver.com");
        customer.setLoginAt(LocalDateTime.now().minusDays(365).plusDays(7));
        // when

        final Customer result = preDormantBatchItemProcessor.process(customer);

        // then
        Assertions.assertThat(result).isEqualTo(customer);
        Assertions.assertThat(result).isNotNull();
    }
    @Test
    @DisplayName("로그인 날짜가 오늘부터 358일 전이아니면 null를 반환애햐한다.")
    void test2(){
        // given
        Customer customer = new Customer("minsoo", "minsoo@naver.com");
        // when
        final Customer result = preDormantBatchItemProcessor.process(customer);
        // then
        Assertions.assertThat(result).isNull();
    }
}