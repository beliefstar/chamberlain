package com.zhenxin.chamberlain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.zhenxin.chamberlain.dao.mapper")
@SpringBootApplication
public class ChamberlainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChamberlainApplication.class, args);
    }

}
