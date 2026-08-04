package com.noobdevs.day10_maven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO extends UserDTO {

    private String password;
    private String department;

    public AdminDTO(Long id, String name, String email, String userType, String password, String department) {
        super(id, name, email, userType);
        this.password = password;
        this.department = department;
    }
}
