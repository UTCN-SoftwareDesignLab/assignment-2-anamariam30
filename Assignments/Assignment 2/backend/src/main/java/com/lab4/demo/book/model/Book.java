package com.lab4.demo.book.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String author;

    @Column
    private String genre;

    @Column
    private long quantity;

    @Column
    private float price;
}
