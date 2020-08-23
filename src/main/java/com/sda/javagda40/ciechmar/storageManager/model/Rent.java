package com.sda.javagda40.ciechmar.storageManager.model;

import lombok.*;

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
    private LocalDate contractDate;
    private float finalPrize;

    @ManyToOne (fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Storage storage;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private AppUser userRent;

    public Rent(LocalDate rentFrom, LocalDate rentTo, LocalDate contractDate, float finalPrize) {
        this.rentFrom = rentFrom;
        this.rentTo = rentTo;
        this.contractDate = contractDate;
        this.finalPrize = finalPrize;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", rentFrom=" + rentFrom +
                ", rentTo=" + rentTo +
                ", contractDate=" + contractDate +
                ", finalPrize=" + finalPrize +
                ", storage= id: " + storage.getId() + " size :" + storage.getSize() +
                ", userRent= surname: " + userRent.getLastName() +
                '}';
    }
}
