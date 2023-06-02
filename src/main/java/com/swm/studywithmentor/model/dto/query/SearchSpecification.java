package com.swm.studywithmentor.model.dto.query;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class SearchSpecification <T> implements Specification<T> {
    private final SearchRequest searchRequest;
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE);
        for(FilterRequest filter : searchRequest.getFilters()) {
            predicate = filter.getOperator().build(root, criteriaBuilder, filter, predicate);
        }
        List<Order> orders = new ArrayList<>();
        for (SortRequest sort : searchRequest.getSorts()) {
            orders.add(sort.getDirection().build(root, criteriaBuilder, sort));
        }
        query.orderBy(orders);
        return predicate;
    }
    public static Pageable getPage(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 0));
    }
}
