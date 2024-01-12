package com.rhtmdwh.batchcampus.application.dormant;

import com.rhtmdwh.batchcampus.EmailProvider;
import com.rhtmdwh.batchcampus.batch.JobExecution;
import com.rhtmdwh.batchcampus.batch.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchExecutionListener implements JobExecutionListener {
    private final EmailProvider emailProvider;

    public DormantBatchExecutionListener() {
        this.emailProvider = new EmailProvider.Fake();
    }


    @Override
    public void beforeJob(JobExecution jobExecution) {
        // no-op
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        emailProvider.send(
                "admin@naver.com",
                "배치 완료 알림",
                "DormantBatchJob 이 수행되었습니다. Status : " + jobExecution.getStatus()
        );
    }
}
