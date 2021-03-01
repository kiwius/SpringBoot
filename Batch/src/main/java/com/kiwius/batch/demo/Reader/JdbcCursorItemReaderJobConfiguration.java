package com.kiwius.batch.demo.Reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcCursorItemReaderJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;

    @Bean
    public Job jdbcCursorItemReaderJob() {
        return jobBuilderFactory.get("jdbcCursorItemReaderJob").start(jdbcCursorItemReader()).build();
    }

    @Bean
    public Step jdbcCursorItemReaderStep() {
        return stepBuilderFactory.get("jdbcCursorItemReaderStep").<Pay, Pay>chunk(chunkSize).reader(jdbcCursorItemReader()).writer(jdbcCursorItemWriter()).build();
    }

    @Bean
    public JdbcCursorItemReader<Pay> jdbcCursorItemReader() {
        String sql = "SELECT id, amount, tx_name, tx_date_tiem FROM pay";
        return new JdbcCursorItemReaderBuilder<Pay>().
                fetchSize(chunkSize) //Database에서 한번에 가져올 데이터 양을 나타냅니다.
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class)) //쿼리 결과를 Java 인스턴스로 매핑하기 위한 mapper입니다.
                .sql(sql) // Reader로  사용할 쿼리문
                .name("jdbcCursorItemReader") //Reader로 사용할 이름을 지정
                .build();

    }

    private ItemWriter<Pay> jdbcCursorItemWriter() {
        return list -> {
            for (Pay pay : list) {
                System.out.println("Current Pay={}", pay);
            }
        };
    }
}
