package dev.gest.invest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "portfolio")
@Getter
@Setter
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name = "Mon portfolio";

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public Portfolio(UUID userId) {
        this.userId = userId;
    }
}
