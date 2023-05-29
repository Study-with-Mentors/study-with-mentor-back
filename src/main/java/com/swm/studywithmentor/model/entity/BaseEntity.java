package com.swm.studywithmentor.model.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Id
    protected UUID id = UUID.randomUUID();
}
