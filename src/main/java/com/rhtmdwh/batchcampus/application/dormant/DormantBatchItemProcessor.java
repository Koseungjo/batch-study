package com.rhtmdwh.batchcampus.application.dormant;

import com.rhtmdwh.batchcampus.batch.ItemProcessor;
import com.rhtmdwh.batchcampus.customer.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DormantBatchItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) {
        final boolean isDormantTarget = LocalDate.now()
                .minusDays(365)
                .isAfter(item.getLoginAt().toLocalDate());

        if (isDormantTarget) {
            item.setStatus(Customer.Status.DORMANT);
            return item;
        } else {
            return null;
        }
    }
}
