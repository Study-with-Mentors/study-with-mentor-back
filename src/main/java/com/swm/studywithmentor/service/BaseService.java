package com.swm.studywithmentor.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class BaseService {
    @Value("${pagesize:20}")
    protected int pageSize;
}
