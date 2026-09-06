package com.example.crudapp.service;

import com.example.crudapp.dto.BookCreateDTO;
import com.example.crudapp.dto.BookDTO;
import com.example.crudapp.mapping.BookMapper;
import com.example.crudapp.model.Book;
import com.example.crudapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга с ID " + bookId + " не найдена"));
        return bookMapper.toDto(book);
    }

    @Transactional
    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        if (bookCreateDTO.getTitle() == null || bookCreateDTO.getTitle().isBlank() ||
                bookCreateDTO.getAuthor() == null || bookCreateDTO.getAuthor().isBlank() ||
                bookCreateDTO.getAmount() == null || bookCreateDTO.getPrice() == null) {
            throw new RuntimeException("Значения title, author, amount и price не могут быть null.");
        }

        Book book = bookMapper.toEntity(bookCreateDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Transactional
    public BookDTO updateBook(Long id, BookCreateDTO bookCreateDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга с ID " + id + " не найдена"));

        existingBook.setTitle(bookCreateDTO.getTitle());
        existingBook.setAuthor(bookCreateDTO.getAuthor());
        existingBook.setAmount(bookCreateDTO.getAmount());
        existingBook.setPrice(bookCreateDTO.getPrice());

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(updatedBook);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException("Книга с ID " + bookId + " не найдена");
        }
        bookRepository.deleteById(bookId);
    }

    public Long countBook() {
        return bookRepository.count();
    }

    public Long countAllBooks() {
        return bookRepository.findAll().stream()
                .mapToLong(Book::getAmount)
                .sum();
    }
}