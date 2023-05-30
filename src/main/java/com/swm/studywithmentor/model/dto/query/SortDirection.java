package com.swm.studywithmentor.model.dto.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public enum SortDirection {
    ASC {
        @Override
        public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequest request) {
            return cb.asc(root.get(request.getKey()));
        }
    },
    DESC {
        @Override
        public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequest request) {
            return cb.desc(root.get(request.getKey()));
        }
    };
    public abstract <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequest request);
}
