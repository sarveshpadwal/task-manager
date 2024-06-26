package com.sp.simpletaskmanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@SpringBootApplication
public class SimpleTaskManagerSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTaskManagerSvcApplication.class, args);
    }

}
