package com.recrutement.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseDTO {
    protected Long id;
    protected Date createdAt;
    protected Date updatedAt;
}
