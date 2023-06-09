package com.swm.studywithmentor.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ActivityDto extends BaseDto {
    @NotEmpty
    private String description;
    @NotEmpty
    private String title;
    @NotNull
    private UUID sessionId;
}
