package com.swm.studywithmentor.model.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    private String key;
    private Operator operator;
    private FieldType fieldType;
    private Object value;
    private Object valueTo;
    private List<Object> values;
}
