package com.noobdevs.dayfive_live_session.controller;

import com.noobdevs.dayfive_live_session.dto.UserRequestDto;
import com.noobdevs.dayfive_live_session.dto.UserResponseDto;
import com.noobdevs.dayfive_live_session.model.User;
import com.noobdevs.dayfive_live_session.repository.UserRepository;
import com.noobdevs.dayfive_live_session.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.create(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<User>> getUsersByPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page,size);
        return new ResponseEntity<>(userService.getAllByPage(pageable),HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/keyword/{text}")
    public ResponseEntity<List<User>> getByContaininginFirstName(@PathVariable String text){
        return new ResponseEntity<>(userService.findByContainingKeywordInFirstName(text),HttpStatus.OK);
    }

    @GetMapping("/custom")
    public ResponseEntity<List<User>> customQuery(@RequestParam(defaultValue = "24") int age){
        return new ResponseEntity<>(userService.getByCustomQuery(age),HttpStatus.OK);
    }

}
