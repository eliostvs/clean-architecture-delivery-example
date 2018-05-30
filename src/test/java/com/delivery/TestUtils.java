package com.delivery;

import org.springframework.beans.BeanUtils;

public class TestUtils {

    public static <I, O> O newInstanceWithProperties(Class<O> clazz, I source, String... ignoreProperties) throws Exception {
        O target = clazz.newInstance();
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }
}
