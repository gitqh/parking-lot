package com.tw.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class CarTest {

    @Test
    public void should_return_diff_cars() {
        assertNotEquals(new Car().getId(), new Car().getId());
    }
}