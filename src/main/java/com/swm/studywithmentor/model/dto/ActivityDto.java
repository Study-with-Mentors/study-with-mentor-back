package com.swm.studywithmentor.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ActivityDto extends BaseDto {
    @NotEmpty
    private String description;
    @NotEmpty
    private String title;
}
