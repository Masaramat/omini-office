package com.vasactrl.exceptions;

import java.io.IOException;

public class DepartmentNotFoundException extends IOException {

    public DepartmentNotFoundException(String message){
        super(message);
    }
}
