package com.example.crudapp.mapping;

import com.example.crudapp.dto.BookCreateDTO;
import com.example.crudapp.dto.BookDTO;
import com.example.crudapp.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookDTO(
                book.getBook_id(),
                book.getTitle(),
                book.getAuthor(),
                book.getAmount(),
                book.getPrice()
        );
    }

    public Book toEntity(BookCreateDTO bookCreateDTO) {
        if (bookCreateDTO == null) {
            return null;
        }
        Book book = new Book();
        book.setTitle(bookCreateDTO.getTitle());
        book.setAuthor(bookCreateDTO.getAuthor());
        book.setAmount(bookCreateDTO.getAmount());
        book.setPrice(bookCreateDTO.getPrice());
        return book;
    }
}