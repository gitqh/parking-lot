package com.tw.domain;

import org.apache.commons.lang3.RandomUtils;

public class Car {
    private String id;
    public Car() {
        this.id = String.valueOf(RandomUtils.nextInt());
    }

    public String getId() {
        return id;
    }
}
