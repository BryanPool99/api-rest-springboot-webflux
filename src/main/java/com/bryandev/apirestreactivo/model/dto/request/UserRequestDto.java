package com.bryandev.apirestreactivo.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    private String name;
    private String email;
}
