package com.swm.studywithmentor.model.exception;

import lombok.Getter;

@Getter
public enum ActionConflict {
    CREATE("creating"),
    UPDATE("updating"),
    DELETE("deleting");

    String name;
    ActionConflict(String name) {
        this.name = name;
    }
}
