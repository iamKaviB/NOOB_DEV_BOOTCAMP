package com.noobdevs.day10_maven.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CLIENT")
@Getter
@Setter
@NoArgsConstructor
public class Client extends User {

    private String company;

    public Client(String name, String email, String password, String company) {
        super(name, email, password);
        this.company = company;
    }
}
