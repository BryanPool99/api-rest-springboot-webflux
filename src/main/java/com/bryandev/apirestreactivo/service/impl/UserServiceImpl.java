package com.bryandev.apirestreactivo.service.impl;

import com.bryandev.apirestreactivo.exceptions.EmailDuplicateException;
import com.bryandev.apirestreactivo.exceptions.UserNotFoundException;
import com.bryandev.apirestreactivo.model.dto.request.UserRequestDto;
import com.bryandev.apirestreactivo.model.entities.UserEntity;
import com.bryandev.apirestreactivo.repositories.UserRepository;
import com.bryandev.apirestreactivo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Flux<UserEntity> getListUser() {
        log.info("Inicio de método de getListUser");
        return userRepository.findAll()
                .doOnError(throwable -> log.error("Error en método getListUser {}", throwable.getMessage()))
                .doOnComplete(() -> log.info("Fin del método getListUser"));
    }

    @Override
    public Mono<UserEntity> getUserFindById(Integer userId) {
        log.info("Inicio del método getUserFindById usando el id {}", userId);
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("El usuario con Id: " + userId + " no existe.")))
                .doOnError(throwable ->
                        log.error("Error en método getUserFindById {}", throwable.getMessage()))
                .doOnSuccess((user) ->
                        log.info("Fin del método getUserFindById con email {}", user.getEmail()));
    }

    @Override
    public Mono<UserEntity> getUserFindByName(String userName) {
        return null;
    }

    @Override
    public Mono<UserEntity> getUserFindByEmail(String email) {
        return null;
    }

    @Override
    public Mono<UserEntity> createUser(UserRequestDto userRequestDto) {
        log.info("Inicio del método crear usuario");
        var newUser = UserEntity.builder()
                .userName(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .build();
        return userRepository.existsByEmail(userRequestDto.getEmail())
                .flatMap(exist -> {
                    if (exist) {
                        return Mono.error(new EmailDuplicateException("Ese email ya está en uso"));
                    }
                    return userRepository.save(newUser)
                            .as(transactionalOperator::transactional)
                            .doOnError(throwable ->
                                    log.error("Error en método createUser {}", throwable.getMessage()))
                            .doOnSuccess((user) ->
                                    log.info("Fin del método createUser"));
                });
    }

    @Override
    public Mono<UserEntity> updateUser(UserRequestDto userRequestDto, Integer userId) {
        log.info("Inicio de método updateUser");
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("El usuario con Id: " + userId + " no existe.")))
                .flatMap(existingUser -> updateUserEntity(existingUser, userRequestDto))
                .as(transactionalOperator::transactional);
    }

    private Mono<UserEntity> updateUserEntity(UserEntity existingUser, UserRequestDto userRequestDto) {
        return userRepository.existsByEmail(userRequestDto.getEmail())
                .flatMap(exists -> {
                    if (exists) return Mono.error(new EmailDuplicateException("Ese email ya está en uso"));
                    else {
                        Optional.ofNullable(userRequestDto.getEmail())
                                .ifPresent(existingUser::setEmail);

                        Optional.ofNullable(userRequestDto.getName())
                                .ifPresent(existingUser::setUserName);


                        return userRepository.save(existingUser)
                                .doOnError(throwable ->
                                        log.error("Error en método updateUserEntity {}", throwable.getMessage()))
                                .doOnSuccess((user) ->
                                        log.info("Fin del método updateUserEntity"));
                    }
                });
    }

    @Override
    public Mono<Void> deleteUserById(Integer userId) {
        log.info("Inicio del método deleteUserById con userId {}", userId);
        return getUserFindById(userId)
                .flatMap(user -> userRepository.deleteById(user.getUserId()));
        /*
        return userRepository.deleteById(userId)
                .doOnError(throwable ->
                        log.error("Error en método deleteUserById {}", throwable.getMessage()))
                .doOnSuccess((user) ->
                        log.info("Fin del método deleteUserById"));
         */
    }
}
