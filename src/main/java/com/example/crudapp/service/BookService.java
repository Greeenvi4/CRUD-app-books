package com.example.crudapp.service;

import com.example.crudapp.model.Book;
import com.example.crudapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Book getBookById(Long book_id) {
        return bookRepository.findById(book_id)
                .orElseThrow(() -> new RuntimeException("Книга с ID " + book_id + " не найден"));
    }

    @Transactional
    public Book createBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank() || book.getAuthor() == null || book.getAuthor().isBlank() || book.getAmount() == null || book.getPrice() == null) {
            throw new RuntimeException("Значения title, author, amount и price не могут быть null.");
        }
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setAmount(updatedBook.getAmount());
        existingBook.setPrice(updatedBook.getPrice());

        return bookRepository.save(existingBook);
    }

    @Transactional
    public void deleteBook(Long book_id) {
        if (!bookRepository.existsById(book_id)) {
            throw new RuntimeException("Пользователь с ID " + book_id + " не найден");
        }
        bookRepository.deleteById(book_id);
    }

    //Выведем количество доступных объектов (книг) в таблице
    public Long countBook(){
        return bookRepository.count();
    }

    //Выведем количество всех экземпляров книг в таблице
    public Long countAllBooks(){
        List<Book> bookList = new ArrayList<>();
        bookList = getAllBooks();
        Long totalAmount = 0L;
        for(Book book : bookList){
            totalAmount += book.getAmount();
        }
        return totalAmount;
    }
}