package com.swm.studywithmentor.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ActivityCreateDtoAlone {
    @NotNull
    private UUID sessionId;
    @NotEmpty
    private String description;
    @NotEmpty
    private String title;
}
