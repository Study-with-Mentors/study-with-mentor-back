package com.swm.studywithmentor.model.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private List<FilterRequest> filters;
    private List<SortRequest> sorts;
    private int page;
    private int size;

    public List<FilterRequest> getFilters() {
        if(Objects.isNull(filters)) return new ArrayList<>();
        return this.filters;
    }

    public List<SortRequest> getSorts() {
        if(Objects.isNull(sorts)) return new ArrayList<>();
        return this.sorts;
    }
}
