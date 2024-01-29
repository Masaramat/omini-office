package com.vasactrl.utilities;

import com.vasactrl.dtos.admin.BranchDto;
import com.vasactrl.models.Branch;
import org.springframework.beans.BeanUtils;

public class BranchEntityUtil {
    public static BranchDto toDto(Branch branch){
        BranchDto dto = new BranchDto();
        BeanUtils.copyProperties(branch, dto);
        return dto;
    }

    public static Branch toModel(BranchDto branchDto){
        Branch branch = new Branch();
        BeanUtils.copyProperties(branchDto, branch);
        return branch;
    }
}
