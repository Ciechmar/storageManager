package com.sda.javagda40.ciechmar.storageManager.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email; // jest też loginem
    private String password;
    private boolean isCompany;
    private String companyName;
    private String NIP;

    public AppUser(String firstName, String lastName, String email, String password, boolean isCompany, String companyName, String NIP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isCompany = isCompany;
        this.companyName = companyName;
        this.NIP = NIP;
    }
}
