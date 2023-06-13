package com.swm.studywithmentor.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FieldCreateDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String code;
    @NotEmpty
    private String description;
}
