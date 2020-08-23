package com.sda.javagda40.ciechmar.storageManager.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
    private String phoneNumber;
    private String email; // jest też loginem
    private String password;
    private boolean isCompany;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Address address;

    @OneToMany(mappedBy = "user") //jeden user może mieć kilka firm
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<CompanyData> companies;


    @OneToMany (mappedBy = "userRent", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Rent> rentals;

    public AppUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isCompany) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.isCompany = isCompany;
    }

}
