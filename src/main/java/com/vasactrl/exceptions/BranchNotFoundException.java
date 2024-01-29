package com.vasactrl.exceptions;

import java.io.IOException;

public class BranchNotFoundException extends IOException {
    public BranchNotFoundException(String message){
        super(message);
    }
}
