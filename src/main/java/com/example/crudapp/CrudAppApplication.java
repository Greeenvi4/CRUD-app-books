package com.example.crudapp;


import org.springframework.boot.SpringApplication;                      //Импортируем класс SpringApplication, который берет все управление SB проектом на себя.Необходимо для строчки: SpringApplication.run(CrudAppApplication.class, args);
import org.springframework.boot.autoconfigure.SpringBootApplication;    //Для аннотации SpringBootApplication

@SpringBootApplication              //Эта аннотация включает в себя три аннотации:
                                    //@Configuration(говорит - здесь есть конфигурация)
                                    //@EnableAutoConfiguration(говорит - настрой все автоматически)
                                    //@ComponentSca(говорит - обойди весь проект и найди все компоненты (ищет: @RestController, @Service, @Repository, @Entity)).
public class CrudAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(CrudAppApplication.class, args);
    }
}