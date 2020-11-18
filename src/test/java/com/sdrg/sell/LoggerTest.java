package com.sdrg.sell;

import jdk.internal.instrumentation.Logger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//@Data

public class LoggerTest {

//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class)

    @Test
    public void test1() {

        String name = "root";
        String password = "111111";
        log.debug("debug......");
        log.info("name:{},password:{}", name, password);
        log.error("error......");

    }
}
