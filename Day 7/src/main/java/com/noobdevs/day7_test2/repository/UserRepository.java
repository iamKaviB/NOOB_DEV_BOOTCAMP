package com.noobdevs.day7_test2.repository;

import com.noobdevs.day7_test2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Forces a single LEFT JOIN query regardless of the entity's FetchType setting.
    // Use this to demonstrate EAGER loading without changing the entity annotation.
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :id")
    Optional<User> findByIdWithOrdersEager(@Param("id") Long id);
}
