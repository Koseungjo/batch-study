package com.rhtmdwh.batchcampus.application.dormant;

import com.rhtmdwh.batchcampus.batch.ItemProcessor;
import com.rhtmdwh.batchcampus.customer.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PreDormantBatchItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) {

        final LocalDate targetDate = LocalDate.now()
                .minusDays(365)
                .plusDays(7);

        if (targetDate.equals(customer.getLoginAt().toLocalDate())){
            return customer;
        }

        return null;
    }
}
