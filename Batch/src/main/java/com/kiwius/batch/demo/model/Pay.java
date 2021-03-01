package com.kiwius.batch.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.datetime.DateFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pay {
    private static final DateFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;

    public Pay(Long amout, String txName, String txDateTime){
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = LocalDateTime.parse(txDateTime, FORMATTER);
    }

    public Pay(Long id, Long amount, String txName, String txDateTime){
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
    }
}
