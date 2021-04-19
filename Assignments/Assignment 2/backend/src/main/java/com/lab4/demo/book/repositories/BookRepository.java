package com.lab4.demo.book.repositories;

import com.lab4.demo.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    public List<Book> findBooksByGenreOrTitleOrAuthor(String genre,String title, String author);
    public List<Book> findBooksByQuantity(long quantity);

}
