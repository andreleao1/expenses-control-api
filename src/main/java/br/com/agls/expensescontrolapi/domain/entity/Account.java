package br.com.agls.expensescontrolapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id", updatable = false, nullable = false)
    private UUID id;

    private String userId;

    private String name;

    private String icon;

    private String balance;

    private Boolean archived;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<Transaction> transactions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
