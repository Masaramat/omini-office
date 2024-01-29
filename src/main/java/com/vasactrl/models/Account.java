package com.vasactrl.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private Long id;

    @Column(value = "gl")
    @Positive(message = "Gl must be a positive number")
    private int gl;

    @Column(value = "name")
    @NotBlank(message = "Account must have a name")
    private String name;

}
