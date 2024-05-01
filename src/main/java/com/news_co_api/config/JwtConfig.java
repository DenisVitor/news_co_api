package com.news_co_api.config;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class JwtConfig {
    private final String secretKey = genValidSecretKey("pingas");
    private final long expiration = hoursToMs(3);

    private long hoursToMs(int i) {
        return i * 60 * 60 * 1000;
    }

    private String genValidSecretKey(String string) {
        int repeat = 1;

        while (string.length() < 22) {
            string = string.repeat(repeat);
            repeat++;
        }
        return string;
    }

}
