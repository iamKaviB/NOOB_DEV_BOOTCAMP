package com.noobdevs.day10_maven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientDTO extends UserDTO {

    private String password;
    private String company;

    public ClientDTO(Long id, String name, String email, String userType, String password, String company) {
        super(id, name, email, userType);
        this.password = password;
        this.company = company;
    }
}
