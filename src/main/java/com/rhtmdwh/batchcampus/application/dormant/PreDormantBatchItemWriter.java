package com.rhtmdwh.batchcampus.application.dormant;

import com.rhtmdwh.batchcampus.EmailProvider;
import com.rhtmdwh.batchcampus.batch.ItemWriter;
import com.rhtmdwh.batchcampus.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreDormantBatchItemWriter implements ItemWriter<Customer> {

    private final EmailProvider emailProvider;

    @Autowired
    public PreDormantBatchItemWriter() {
        this.emailProvider = new EmailProvider.Fake();
    }

    public PreDormantBatchItemWriter(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    @Override
    public void write(Customer customer) {
       emailProvider.send(customer.getEmail(),
               "곧 휴면 계정으로 전환됩니다.",
               "휴면 계정으로 허용되기를 원치 않으신다면 1주일 내에 로그인을 해주세요.");
    }
}
