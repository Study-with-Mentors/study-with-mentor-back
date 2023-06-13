package com.swm.studywithmentor.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ActivityCreateDto {
    @NotEmpty
    private String description;
    @NotEmpty
    private String title;
}
