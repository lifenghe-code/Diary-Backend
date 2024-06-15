package com.lifh.diary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lifh.diary.mapper")

public class DiaryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryBackendApplication.class, args);
    }

}
