package com.sda.javagda40.ciechmar.storageManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private StorageSize size;
    private boolean forSanepidUse;
    private boolean isGarage;

    private Floor floor;
    private StorageColor color;

    private StorageStatus status;


}
