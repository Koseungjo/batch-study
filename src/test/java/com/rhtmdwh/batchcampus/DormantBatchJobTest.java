package com.rhtmdwh.batchcampus;

import com.rhtmdwh.batchcampus.batch.BatchStatus;
import com.rhtmdwh.batchcampus.batch.Job;
import com.rhtmdwh.batchcampus.batch.JobExecution;
import com.rhtmdwh.batchcampus.batch.TaskletJob;
import com.rhtmdwh.batchcampus.customer.Customer;
import com.rhtmdwh.batchcampus.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class DormantBatchJobTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Job dormantBatchJob;

    @BeforeEach
    public void setup(){
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 시간이 1년을 경과한 고객이 세명이고, 일년 이내에 로그인한 고객이 다섯명이면 3명의 고객이 휴면 전환 대상자이다.")
    void test1() {
        // given
        saveCustomer(366);
        saveCustomer(366);
        saveCustomer(366);

        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);

        // when
        final JobExecution result = dormantBatchJob.execute();

        // then
        final long dormantCount = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == Customer.Status.DORMANT)
                .count();

        Assertions.assertThat(dormantCount).isEqualTo(3);
        Assertions.assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    @DisplayName("고객이 열명이 있지만 모두 다 휴면전환대상이면(1년 경과한사람) 휴면 전환 대상은 10명이다")
    void test2() {
        // given
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);

        // when
        final JobExecution result = dormantBatchJob.execute();

        // then
        final long dormantCount = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == Customer.Status.DORMANT)
                .count();

        Assertions.assertThat(dormantCount).isEqualTo(10);
        Assertions.assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    }

    @Test
    @DisplayName("고객이 없는 경우에도 배치는 정상동작 해야한다.")
    void test3() {
        // when
        final JobExecution result = dormantBatchJob.execute();

        // then
        final long dormantCount = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == Customer.Status.DORMANT)
                .count();

        Assertions.assertThat(dormantCount).isEqualTo(0);
        Assertions.assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    @DisplayName("배치가 실패하면 배치 스테이터스는 FAILED를 반환해야한다.")
    void test(){
        final Job job = new TaskletJob(null);

        final JobExecution result = job.execute();

        Assertions.assertThat(result.getStatus()).isEqualTo(BatchStatus.FAILED);

    }

    @Test
    @DisplayName("358일 전에 로그인한 고객에게 휴면계정 예정자라고 메일을 발송해야 한다.")
    void test5(){
        // given
        saveCustomer(358);
        saveCustomer(358);
        saveCustomer(358);
        saveCustomer(35);
        saveCustomer(35);

        // when
        // then
        dormantBatchJob.execute();
    }

    private void saveCustomer(long loginMinusDays) {
        final String uuid = UUID.randomUUID().toString();
        final Customer test = new Customer(uuid,uuid + "test@naver.com");
        test.setLoginAt(LocalDateTime.now().minusDays(loginMinusDays));
        customerRepository.save(test);
    }
}