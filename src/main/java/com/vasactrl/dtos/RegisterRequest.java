package com.vasactrl.dtos;

import com.vasactrl.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private Role role;

    private Long departmentId;

    private Long branchId;

    private String password;

}
