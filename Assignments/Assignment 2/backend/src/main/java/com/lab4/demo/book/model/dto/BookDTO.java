package com.lab4.demo.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private long id;
    private String title;
    private String author;
    private String genre;
    private long quantity;
    private long price;
}
