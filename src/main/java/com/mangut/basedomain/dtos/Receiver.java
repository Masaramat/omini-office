package com.mangut.basedomain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receiver {
    private Long id;
    private String email;
    private String name;
    private String password;

}
