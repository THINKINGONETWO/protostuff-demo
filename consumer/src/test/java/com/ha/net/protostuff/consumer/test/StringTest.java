package com.ha.net.protostuff.consumer.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTest {

    private static final Logger logger = LoggerFactory.getLogger(StringTest.class);

    public static void main(String[] args) {

        String productCodeStr = "P0606103,P0606104,";
        String[] productCodes = productCodeStr.split(",");

        logger.info("productCode :" + productCodes[0]);
    }
}
