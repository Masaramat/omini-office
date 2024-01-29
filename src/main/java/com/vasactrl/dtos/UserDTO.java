package com.vasactrl.dtos;

import com.vasactrl.models.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Long departmentId;
    private Long branchId;
}
