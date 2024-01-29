package com.vasactrl.utilities;

import com.vasactrl.dtos.admin.DepartmentDto;
import com.vasactrl.models.Department;
import org.springframework.beans.BeanUtils;

public class DepartmentModelUtil {

    public static DepartmentDto toDto(Department department){
        DepartmentDto dto = new DepartmentDto();
        BeanUtils.copyProperties(department, dto);
        return dto;
    }

    public static Department toModel(DepartmentDto departmentDTO){
        Department department = new Department();
        BeanUtils.copyProperties(departmentDTO, department);
        return department;
    }

}
