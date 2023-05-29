package com.swm.studywithmentor.model.exception;

import lombok.Getter;

@Getter
public enum ActionConflict {
    CREATE("creating"),
    UPDATE("updating");

    String name;
    ActionConflict(String name) {
        this.name = name;
    }
}
