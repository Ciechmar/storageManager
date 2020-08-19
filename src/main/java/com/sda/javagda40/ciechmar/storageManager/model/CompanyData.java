package com.sda.javagda40.ciechmar.storageManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompanyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String NIP;

    @OneToOne (mappedBy = "company")
    @EqualsAndHashCode.Exclude
    private Address address;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private AppUser user;

    public CompanyData(String companyName, String NIP) {
        this.companyName = companyName;
        this.NIP = NIP;
    }

}
