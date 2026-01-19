package com.bryandev.apirestreactivo.controller;

import com.bryandev.apirestreactivo.model.dto.request.UserRequestDto;
import com.bryandev.apirestreactivo.model.entities.UserEntity;
import com.bryandev.apirestreactivo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Flux<UserEntity> getListUsers() {
        return userService.getListUser();
    }

    @GetMapping("/{userId}")
    public Mono<UserEntity> getUserById(@PathVariable Integer userId) {
        return userService.getUserFindById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserEntity> createNewUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PatchMapping("/{userId}")
    public Mono<UserEntity> updateUser(@PathVariable Integer userId, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(userRequestDto, userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUserById(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }
}
