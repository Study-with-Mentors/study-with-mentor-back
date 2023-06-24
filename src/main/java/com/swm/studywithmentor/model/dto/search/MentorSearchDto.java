package com.swm.studywithmentor.model.dto.search;

import com.swm.studywithmentor.model.entity.user.Education;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MentorSearchDto extends BaseSearchDto {
    private String name;
    private List<Education> degrees;
    // Enter field code
    private List<String> fields;
}
