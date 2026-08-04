package com.noobdevs.day10_maven.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

    private String department;

    public Admin(String name, String email, String password, String department) {
        super(name, email, password);
        this.department = department;
    }
}
