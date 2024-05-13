package com.springproject.productservice.dtos;

import com.springproject.productservice.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends BaseModel {
    private String name;
}
