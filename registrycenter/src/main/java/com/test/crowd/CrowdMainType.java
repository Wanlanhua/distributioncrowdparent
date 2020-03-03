package com.test.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @autjor WangLanHua
 * @date 2020/1/1-16:48
 */
@EnableEurekaServer
@SpringBootApplication
public class CrowdMainType {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainType.class, args);
    }
}