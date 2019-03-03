package com.benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@RequestMapping("bench")
public class BenchmarkApplication extends SpringBootServletInitializer {

    @GetMapping("/big")
    @ResponseBody
    public Resource getExampleBigJson(){
        return new ClassPathResource("/static/big.json");
    }


    @GetMapping("/medium")
    @ResponseBody
    public Resource getExampleMediumJson(){
        return new ClassPathResource("/static/medium.json");
    }



    @GetMapping("/small")
    @ResponseBody
    public Resource getExampleSmallJson(){
        return new ClassPathResource("/static/small.json");
    }

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkApplication.class, args);
    }

}
