package com.bryandev.apirestreactivo.service;

import com.bryandev.apirestreactivo.model.dto.request.UserRequestDto;
import com.bryandev.apirestreactivo.model.entities.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    //LISTAR USUARIOS
    Flux<UserEntity> getListUser();

    //OBTENER USUARIO POR ID
    Mono<UserEntity> getUserFindById(Integer userId);

    //OBTENER USUARIO POR NOMBRE
    Mono<UserEntity> getUserFindByName(String userName);

    //OBTENER USUARIO POR EMAIL
    Mono<UserEntity> getUserFindByEmail(String email);

    //CREACION DE UN USUARIO
    Mono<UserEntity> createUser(UserRequestDto userRequestDto);

    //ACTUALIZACION DE UN USUARIO
    Mono<UserEntity> updateUser(UserRequestDto userRequestDto,Integer userId);

    //ELIMINACION DE USUARIO
    Mono<Void> deleteUserById(Integer userId);
}
