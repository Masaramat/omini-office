package com.vasactrl.dtos.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Data
@ToString
@RequiredArgsConstructor
public class AccountDto {
    private Long id;
    private int gl;
    private String name;
}
