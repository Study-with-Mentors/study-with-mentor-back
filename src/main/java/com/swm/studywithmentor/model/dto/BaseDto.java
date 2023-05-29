package com.swm.studywithmentor.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class BaseDto {
    private UUID id;
    private Long version;
}
