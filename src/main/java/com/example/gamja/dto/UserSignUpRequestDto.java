package com.example.gamja.dto;

import com.example.gamja.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {
    private String name;
    private String email;
    private String password;

    public User toSignUpUser(){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
