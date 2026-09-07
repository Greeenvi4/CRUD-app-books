package com.example.crudapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Сущность, представляющая книгу в системе складского учета книг.
 * <p>
 * Этот класс является JPA-сущностью и отображается на таблицу "books" в базе данных.
 * Использует Lombok для автоматической генерации геттеров, сеттеров, конструкторов и других методов.
 * </p>
 *
 * @author Andrus Gregory
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    /**
     * Уникальный идентификатор книги.
     * <p>
     * Значение генерируется автоматически с использованием стратегии идентификации
     * {@link GenerationType#IDENTITY}, что обеспечивает автоинкремент в базе данных.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;

    /**
     * Название книги.
     * <p>
     * Обязательное поле, не может быть null в базе данных.
     * </p>
     */
    @Column(nullable = false)
    private String title;

    /**
     * Автор книги.
     * <p>
     * Обязательное поле, не может быть null в базе данных.
     * </p>
     */
    @Column(nullable = false)
    private String author;

    /**
     * Количество экземпляров книги в наличии.
     * <p>
     * Обязательное поле, не может быть null в базе данных.
     * </p>
     */
    @Column(nullable = false)
    private Integer amount;

    /**
     * Цена книги.
     * <p>
     * Обязательное поле (NOT NULL в БД).
     * Точность: 10 знаков, 2 знака после запятой.
     * </p>
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}