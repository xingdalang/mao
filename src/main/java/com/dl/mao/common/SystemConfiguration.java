package com.dl.mao.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingguanghui
 * @create 2018-05-30 18:27
 **/
@Data
@Configuration
public class SystemConfiguration {
    @Value("${rsa.key.private}")
    private String rsaPrivateKey;
}
