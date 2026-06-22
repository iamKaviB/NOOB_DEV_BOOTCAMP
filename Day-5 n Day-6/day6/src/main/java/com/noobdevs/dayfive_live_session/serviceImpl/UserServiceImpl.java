package com.noobdevs.dayfive_live_session.serviceImpl;

import com.noobdevs.dayfive_live_session.dto.UserRequestDto;
import com.noobdevs.dayfive_live_session.dto.UserResponseDto;
import com.noobdevs.dayfive_live_session.model.User;
import com.noobdevs.dayfive_live_session.repository.UserRepository;
import com.noobdevs.dayfive_live_session.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setAge(userRequestDto.getAge());
        String uniqueId = userRequestDto.getFirstName()+userRequestDto.getLastName()+Math.random();
        user.setUniqueId(uniqueId);
        user =userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUniqueId(user.getUniqueId());
        return userResponseDto;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isEmpty()){
            return null;
        }
        return optUser.get();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }

    @Override
    public List<User> findByContainingKeywordInFirstName(String text) {
        return userRepository.findAllByFirstNameContaining(text);
    }

    @Override
    public List<User> getByCustomQuery(Integer age) {
        return userRepository.methodA(age);
    }
}
