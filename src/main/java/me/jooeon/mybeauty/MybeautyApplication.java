package me.jooeon.mybeauty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MybeautyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybeautyApplication.class, args);
    }

}
