package com.sda.javagda40.ciechmar.storageManager.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String postCode;
    private String street;
    private String buildingNumber;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUser user;

}
