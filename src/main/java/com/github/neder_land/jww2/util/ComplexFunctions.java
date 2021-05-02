package com.github.neder_land.jww2.util;

import com.google.common.base.Preconditions;

import java.util.function.Function;

public class ComplexFunctions {
    public static<I,C,O> Function<I,O> chain(Function<I,C> before, Function<C,O> after) {
        Preconditions.checkArgument(before != null && after != null);
        return after.compose(before);
    }
}
