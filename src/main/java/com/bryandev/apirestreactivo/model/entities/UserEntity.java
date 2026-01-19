package com.bryandev.apirestreactivo.model.entities;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "users", schema = "test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @Column("id")
    private Integer userId;

    @Column("name")
    @NotNull
    private String userName;

    @Column("email")
    @NotNull
    private String email;
}
