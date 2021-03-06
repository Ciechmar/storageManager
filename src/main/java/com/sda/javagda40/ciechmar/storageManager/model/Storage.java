package com.sda.javagda40.ciechmar.storageManager.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private StorageSize size;
    private int doorNumber;
    private boolean forSanepidUse;
    private boolean isGarage;
    private float standardPrize;
    private Floor floor;
    @Enumerated(value = EnumType.STRING)
    private StorageColor color;
    @Enumerated(value = EnumType.STRING)
    private StorageStatus status;

    @OneToMany(mappedBy = "storage", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Rent> storageRentals;

    public Storage(StorageSize size, int doorNumber, boolean forSanepidUse, boolean isGarage, float standardPrize, Floor floor, StorageColor color, StorageStatus status) {
        this.size = size;
        this.doorNumber = doorNumber;
        this.forSanepidUse = forSanepidUse;
        this.isGarage = isGarage;
        this.standardPrize = standardPrize;
        this.floor = floor;
        this.color = color;
        this.status = status;
    }
}
