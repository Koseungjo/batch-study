package com.rhtmdwh.batchcampus.application.dormant;

import com.rhtmdwh.batchcampus.EmailProvider;
import com.rhtmdwh.batchcampus.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class PreDormantBatchItemWriterTest {

    private PreDormantBatchItemWriter preDormantBatchItemWriter;

    @BeforeEach
    public void setup(){
        this.preDormantBatchItemWriter = new PreDormantBatchItemWriter();
    }

    @Test
    @DisplayName("1주일 뒤에 휴면 계정전환 예정자라고 이메일을 전송해야한다.")
    void test1(){

        // given
        final EmailProvider mockEmailProvider = mock(EmailProvider.class);
        this.preDormantBatchItemWriter = new PreDormantBatchItemWriter(mockEmailProvider);

        final Customer customer = new Customer("minsoo","minsoo@naver.com");

        // when
        preDormantBatchItemWriter.write(customer);

        // then

        verify(mockEmailProvider, atLeast(1)).send(any(),any(),any());

    }
}