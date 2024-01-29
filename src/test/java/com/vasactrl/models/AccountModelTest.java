package com.vasactrl.models;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class AccountModelTest {
    private Validator validator;


    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAccount(){
        Account account = new Account(1L, 123456, "Test Account");
        assertNotNull(account);
        assertEquals(account.getGl(), 123456);
    }

    @Test
    public void accountValidationFailTest(){
        Account account = new Account();
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<Account> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(2, errorNo);

    }


}
