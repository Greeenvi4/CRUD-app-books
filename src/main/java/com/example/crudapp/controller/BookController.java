package com.example.crudapp.controller;

import com.example.crudapp.dto.BookCreateDTO;
import com.example.crudapp.dto.BookDTO;
import com.example.crudapp.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookCreateDTO bookCreateDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<String> countBook() {
        return ResponseEntity.ok("Количество записей: " + bookService.countBook());
    }

    @GetMapping("/countall")
    public ResponseEntity<String> countAllBook() {
        return ResponseEntity.ok("Количество всех экземпляров книг: " + bookService.countAllBooks());
    }
}