package com.rhtmdwh.batchcampus.batch;

public interface ItemProcessor <I, O> {

    O process(I item);
}
