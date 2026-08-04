//package com.noobdevs.day10_maven.config;
//
//import com.noobdevs.day10_maven.model.Admin;
//import com.noobdevs.day10_maven.repository.AdminRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * Creates the first admin account on startup. POST /api/admins is locked to
// * ROLE_ADMIN (see SecurityConfig), so without this seed there would be no
// * way to create the very first admin. Every admin after this one is created
// * by an already-authenticated admin through POST /api/admins.
// */
//@Component
//@RequiredArgsConstructor
//public class AdminSeeder implements ApplicationRunner {
//
//    private final AdminRepository adminRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Value("${app.admin.name}")
//    private String name;
//
//    @Value("${app.admin.email}")
//    private String email;
//
//    @Value("${app.admin.password}")
//    private String password;
//
//    @Value("${app.admin.department}")
//    private String department;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        if (adminRepository.count() == 0) {
//            Admin admin = new Admin(name, email, passwordEncoder.encode(password), department);
//            adminRepository.save(admin);
//        }
//    }
//}
