package com.sda.javagda40.ciechmar.storageManager.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

    @OneToMany (mappedBy = "address", fetch = FetchType.EAGER) //pod jednym adresem kilku właścicieli może być (np.: żona i mąż oddzielnie i jedszcze siedziba firmy)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AppUser> ussers;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private CompanyData company;

    public Address(String city, String postCode, String street, String buildingNumber) {
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.buildingNumber = buildingNumber;
    }
}
