package com.rhtmdwh.batchcampus.application.dormant;

import com.rhtmdwh.batchcampus.EmailProvider;
import com.rhtmdwh.batchcampus.batch.ItemWriter;
import com.rhtmdwh.batchcampus.customer.Customer;
import com.rhtmdwh.batchcampus.customer.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchItemWriter implements ItemWriter<Customer> {
    private final CustomerRepository customerRepository;
    private final EmailProvider emailProvider;

    public DormantBatchItemWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void write(Customer item) {
        customerRepository.save(item);
        emailProvider.send(item.getEmail(), "휴면 전환 안내메일입니다.", "휴면전환 되었습니다.");
    }
}
