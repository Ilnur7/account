package ru.iteco.account.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString(of = {"id", "balance"})
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_create", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(name = "balance", scale = 2)
    private BigDecimal balance;

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    public Account(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }
}
