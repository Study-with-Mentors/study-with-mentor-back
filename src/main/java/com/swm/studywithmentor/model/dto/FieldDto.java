package com.swm.studywithmentor.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FieldDto extends BaseDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
