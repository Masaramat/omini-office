package com.vasactrl.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestResponse<T> implements RequestResponseRepo<T> {
    private T data;
    private String message;


    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
