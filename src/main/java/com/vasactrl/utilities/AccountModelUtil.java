package com.vasactrl.utilities;

import com.vasactrl.dtos.admin.AccountDto;
import com.vasactrl.models.Account;
import org.springframework.beans.BeanUtils;

public class AccountModelUtil {
    public static AccountDto toDto(Account account){
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        return accountDto;
    }

    public static Account toModel(AccountDto accountDto){
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        return account;
    }
}
