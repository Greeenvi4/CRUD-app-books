package com.example.crudapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;


    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer price;
}