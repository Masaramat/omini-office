package com.vasactrl.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "departments")
@Data
@RequiredArgsConstructor
public class Department {
    @Id
    private Long id;

    @Column(value = "name")
    @NotBlank(message = "Dept must have a name")
    private String name;
}
