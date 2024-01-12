package com.rhtmdwh.batchcampus.application.step;

import com.rhtmdwh.batchcampus.batch.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StepExampleBatchConfigurationTest {

    @Autowired
    private Job strepExampleBatchJob;

    @Test
    void test(){
        strepExampleBatchJob.execute();
    }

}