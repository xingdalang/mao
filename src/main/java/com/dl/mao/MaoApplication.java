package com.dl.mao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dl.mao.dao")
public class MaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaoApplication.class, args);
	}
}
