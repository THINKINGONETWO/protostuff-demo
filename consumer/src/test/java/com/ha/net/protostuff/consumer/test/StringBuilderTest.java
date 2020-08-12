package com.ha.net.protostuff.consumer.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(StringBuilderTest.class);
    public static void main(String[] args) {

        StringBuilder productCodeBuilder = new StringBuilder();
        productCodeBuilder.append("P0606103");
        productCodeBuilder.append(",");
        productCodeBuilder.append("P0606104");
        productCodeBuilder.append(",");
        String productCodesStr = productCodeBuilder.toString();
        logger.info("result : "+productCodeBuilder.toString());
    }
}
