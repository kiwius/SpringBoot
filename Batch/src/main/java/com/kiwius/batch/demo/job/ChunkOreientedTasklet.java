package com.kiwius.batch.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.item.Chunk;
import org.springframework.batch.core.step.item.ChunkProcessor;
import org.springframework.batch.core.step.item.ChunkProvider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.lang.module.Configuration;


@Slf4j
public class ChunkOreientedTasklet<I> implements Tasklet {

    private static final String INPUTS_KEYS = "INPUTS";

    private final ChunkProcessor<I> chunkProcessor;

    private final ChunkProvider chunkProvider;

    private boolean buffering = true;

    public ChunkOreientedTasklet(ChunkProvider<I> chunkProvider, ChunkProcessor<I> cuChunkProcessor){
        this.chunkProvider = chunkProvider;
        this.chunkProcessor = cuChunkProcessor;
    }

    public ReapeatSatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception{
        Chunk<I> inputs = (Chunk<I>) chunkContext.getAttribute(INPUTS_KEYS);

        if(inputs == null){ //Reader에서 데이터를 가져옴
            inputs = chunkProvider.provide(contribution);
            if(buffering){
                chunkContext.setAttribute(INPUTS_KEYS, inputs);
            }
        }

        chunkProcessor.process(contribution, inputs); //Processor & Writer 처리
        chunkProvider.postProcess(Configuration, inputs);

        if(inputs.isBusy()){
            return RepeatStatus.CONTINUABLE;
        }

        chunkContext.removeAttribute(INPUTS_KEYS);
        chunkContext.setComplete();

        return RepeatStatus.continueIf(!inputs.isEnd());
    }
}
