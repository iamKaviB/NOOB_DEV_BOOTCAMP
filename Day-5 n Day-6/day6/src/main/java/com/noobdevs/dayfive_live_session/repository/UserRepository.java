package com.noobdevs.dayfive_live_session.repository;

import com.noobdevs.dayfive_live_session.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAllByFirstNameContaining(String text);

    @Query("SELECT u FROM User u WHERE u.age = :age")
    List<User> methodA(@Param("age") Integer age);
}
