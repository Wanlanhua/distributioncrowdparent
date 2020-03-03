package com.test.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @autjor WangLanHua
 * @date 2020/1/1-17:21
 */
@MapperScan("com.test.crowd.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class CrowdMainType {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainType.class, args);
    }

}
