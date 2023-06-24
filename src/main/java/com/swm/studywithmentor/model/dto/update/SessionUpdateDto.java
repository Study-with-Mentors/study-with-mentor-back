package com.swm.studywithmentor.model.dto.update;

import com.swm.studywithmentor.model.dto.BaseDto;
import com.swm.studywithmentor.model.entity.session.SessionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionUpdateDto extends BaseDto {
    private long sessionNum;
    private String sessionName;
    private SessionType type;
    private String description;
    private String resource;
}
