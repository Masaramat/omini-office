package com.mangut.basedomain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailEvent implements Serializable {
    private String message;
    private String status;
    private Receiver receiver;

}
