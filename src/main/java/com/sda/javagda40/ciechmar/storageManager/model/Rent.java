package com.sda.javagda40.ciechmar.storageManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate rentFrom;
    private LocalDate rentTo;
    private float finalPrize;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Storage storage;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private AppUser UserRent;

    public Rent(LocalDate rentFrom, LocalDate rentTo, float finalPrize) {
        this.rentFrom = rentFrom;
        this.rentTo = rentTo;
        this.finalPrize = finalPrize;
    }

}
