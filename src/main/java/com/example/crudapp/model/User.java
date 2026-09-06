package com.example.crudapp.model;

import jakarta.persistence.*; //Для @Entity, @Table, @Id, @GeneratedValue, @Column (Spring Data JPA, Hibernate)
import lombok.*;              //Для @Data, @NoArgsConstructor, @AllArgsConstructor

@Entity                     //(Spring Data JPA, Hibernate) Эта аннотация помечает наш класс User как сущность, указывая на то, что объекты этого класса должны сохраняться в БД.
@Table(name = "users")      //(Spring Data JPA, Hibernate) Эта аннотация позволяет явно указать имя таблицы в БД.
@Data                       //(Lombok) генерирует getters/setters/toString/equals/hashCode и конструкторы для всех полей. Что значительно уменьшает количество кода.
@NoArgsConstructor          //(Lombok) генерирует конструктор без аргументов.
@AllArgsConstructor         //(Lombok) генерирует конструктор, которые принимает все поля класса, в том порядке в котором они объявлены в классе.

public class User {

    //Поле в Java, которое отвечает за создание поля сущности в SQL users_id (первичный ключ). Заменяет код на SQL: users_id INT PRIMARY KEY AUTO_INCREMENT.
    @Id                                                 //(Spring Data JPA, Hibernate)Помечает данное поле, как первичный ключ (primary key) нашей сущности. (заменяет код в SQL: users_id INT)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //(Spring Data JPA, Hibernate)Автоинкремент. Позволяет автомтически присваивать значения первичному ключу (заменяет код в SQL: PRIMARY KEY AUTO_INCREMENT )
    private Long users_id;

    //Поле в Java, которое отвечает за создание поля сущности в SQL name. Заменяет код на SQL: name VARCHAR(255) NOT NULL.
    @Column(nullable = false)                           //(Spring Data JPA, Hibernate)Аннотация для отображения конкретного столбца сущности, которая позволяет его настриавать. В нашем случае (nullable = false) говорит о том, что значения в данном поле не можеть быть null.
    private String name;

    //Поле в Java, которое отвечает за создание поля сущности в SQL email. Заменяет код на SQL: name VARCHAR(255) NOT NULL UNIQUE.
    @Column(nullable = false, unique = true)            //(Spring Data JPA, Hibernate)Поле таблицы (email) - не может быть NULL и должно быть уникальным.
    private String email;
}