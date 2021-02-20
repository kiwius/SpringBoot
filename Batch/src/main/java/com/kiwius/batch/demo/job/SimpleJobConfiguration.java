package com.kiwius.batch.demo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 di를 위한 lombok 어노테이션
@Configuration // 스프링 배치의 모든 잡은 configuration을 등록하여 사용
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob").start(simpleStep1()).build();
        //'simple' job이란 이름의 batch job을  생성
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1").tasklet((contribution, chunkContext) -> {
            // 'simpleStep1'이란 이름의 batch step을  생성
            // step에서 수행될 tasklet을 생성
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>this is step1");
            return RepeatStatus.FINISHED;
        }).build();
    }
}
