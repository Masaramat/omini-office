package com.vasactrl.dtos;

import com.vasactrl.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse<T> {
    private T user;
    private String token;
}
