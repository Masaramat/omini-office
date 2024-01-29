package com.vasactrl.dtos.admin;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class BranchDto {
    private Long id;
    private String name;
    private String location;
    private double approvalLimit;
}
