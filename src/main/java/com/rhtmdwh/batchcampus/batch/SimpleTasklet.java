package com.rhtmdwh.batchcampus.batch;


import org.springframework.stereotype.Component;

public class SimpleTasklet<I, O> implements Tasklet {

    private final ItemReader<I> itemReader;
    private final com.rhtmdwh.batchcampus.batch.ItemProcessor<I, O> ItemProcessor;
    private final ItemWriter itemWriter;

    public SimpleTasklet(ItemReader<I> itemReader, ItemProcessor<I, O> ItemProcessor, ItemWriter<O> itemWriter) {
        this.itemReader = itemReader;
        this.ItemProcessor = ItemProcessor;
        this.itemWriter = itemWriter;
    }

    @Override
    public void execute() {
        while (true) {
            final I read = itemReader.read();
            if (read == null) break;

            final O process = ItemProcessor.process(read);
            if (process == null) continue;

            itemWriter.write(process);
        }
    }
}
