package com.noobdevs.dayfive_live_session.service;


import com.noobdevs.dayfive_live_session.dto.UserRequestDto;
import com.noobdevs.dayfive_live_session.dto.UserResponseDto;
import com.noobdevs.dayfive_live_session.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResponseDto create(UserRequestDto user);
    List<User> getAll();
    Page<User> getAllByPage(Pageable pageable);
    User findById(Long id);
    User update(User user);
    User delete(Long id);
    List<User> findByContainingKeywordInFirstName(String text);
    List<User> getByCustomQuery(Integer age);
}
