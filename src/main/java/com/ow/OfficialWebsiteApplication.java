package com.ow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lavnote
 */
@MapperScan("com.ow.mapper")
@SpringBootApplication
public class OfficialWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficialWebsiteApplication.class, args);
    }

}
