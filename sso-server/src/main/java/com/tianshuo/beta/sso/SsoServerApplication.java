package com.tianshuo.beta.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tianshuo
 */
@SpringBootApplication
@MapperScan("com.tianshuo.beta.sso.dao")
public class SsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
        System.out.println("\n" +
                "     _________ \n" +
                "    / ======= \\ \n" +
                "   / __________\\ \n" +
                "  | ___________ | \n" +
                "  | | -       | | \n" +
                "  | |         | | \n" +
                "  | |_________| |________________ \n" +
                "  \\=____________/                ) \n" +
                "  / \"\"\"\"\"\"\"\"\"\"\" \\               / \n" +
                " / ::::::::::::: \\          =D-' \n" +
                "(_________________) \n");
        System.out.println("启动成功!");
    }

}
