package com.bryandev.apirestreactivo.repositories;

import com.bryandev.apirestreactivo.model.entities.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Integer> {
    Mono<UserEntity> findByEmail(String email);

    @Query("""
            SELECT EXISTS(SELECT 1 FROM users u where u.id = :userId )
            """)
    Mono<Boolean> existUserById(Integer userId);

    Mono<Boolean> existsByEmail(String email);
}
