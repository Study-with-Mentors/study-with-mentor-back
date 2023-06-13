package com.swm.studywithmentor.model.dto.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class BaseSearchDto {
    private Sort.Direction direction = Sort.Direction.ASC;
    private String orderBy;

    // alias for `direction` as `dir`
    public void setDir(Sort.Direction dir) {
        direction = dir;
    }
}
