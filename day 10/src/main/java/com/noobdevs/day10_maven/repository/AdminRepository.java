package com.noobdevs.day10_maven.repository;

import com.noobdevs.day10_maven.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
