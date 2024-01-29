package com.vasactrl.dtos.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class DepartmentDto {
    private Long id;
    private String name;
}
