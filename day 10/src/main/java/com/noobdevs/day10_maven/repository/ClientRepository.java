package com.noobdevs.day10_maven.repository;

import com.noobdevs.day10_maven.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
