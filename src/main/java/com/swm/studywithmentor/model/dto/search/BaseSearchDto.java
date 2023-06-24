package com.swm.studywithmentor.model.dto.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class BaseSearchDto {
    @JsonIgnore
    private Sort.Direction direction = Sort.Direction.ASC;
    private String orderBy;
    private int page;

    // alias for `direction` as `dir`
    public void setDir(Sort.Direction dir) {
        direction = dir;
    }
}
