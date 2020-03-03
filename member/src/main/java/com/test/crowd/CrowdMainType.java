package com.test.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.Async;

/**
 * @autjor WangLanHua
 * @date 2020/1/1-17:21
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class CrowdMainType {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainType.class, args);
    }

}
