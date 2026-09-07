package com.example.crudapp.service;

import com.example.crudapp.dto.BookCreateDTO;
import com.example.crudapp.dto.BookDTO;
import com.example.crudapp.mapping.BookMapper;
import com.example.crudapp.model.Book;
import com.example.crudapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для управления операциями с книгами.
 * <p>
 * Предоставляет методы для выполнения CRUD операций над сущностями книг,
 * включая создание, чтение, обновление и удаление, а также дополнительные
 * методы для получения статистической информации.
 * </p>
 *
 * <p>Все методы по умолчанию выполняются в режиме только для чтения
 * ({@code readOnly = true}), за исключением методов изменения данных,
 * которые явно аннотированы {@code @Transactional}.</p>
 *
 * @author Andrus Gregory
 * @see Book
 * @see BookDTO
 * @see BookCreateDTO
 * @see BookRepository
 * @see BookMapper
 */
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param bookRepository репозиторий для доступа к данным книг
     * @param bookMapper     маппер для преобразования между сущностями и DTO
     */
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Получает список всех книг.
     *
     * @return список DTO всех книг, отсортированный в порядке их сохранения в базе данных
     */
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает книгу по её уникальному идентификатору.
     *
     * @param bookId уникальный идентификатор книги (не может быть {@code null})
     * @return DTO найденной книги
     * @throws RuntimeException если книга с указанным ID не найдена
     */
    public BookDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга с ID " + bookId + " не найдена"));
        return bookMapper.toDto(book);
    }

    /**
     * Создает новую книгу на основе предоставленных данных.
     * <p>
     * Метод проверяет, что все обязательные поля (название, автор, количество, цена)
     * заполнены и не являются {@code null}. Цена автоматически округляется до двух
     * десятичных знаков с использованием {@code RoundingMode.HALF_UP}.
     * </p>
     *
     * @param bookCreateDTO DTO с данными для создания книги (не может быть {@code null})
     * @return DTO созданной книги с присвоенным идентификатором
     * @throws RuntimeException если какое-либо из обязательных полей равно {@code null} или пустое
     */
    @Transactional
    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        if (bookCreateDTO.getTitle() == null || bookCreateDTO.getTitle().isBlank() ||
                bookCreateDTO.getAuthor() == null || bookCreateDTO.getAuthor().isBlank() ||
                bookCreateDTO.getAmount() == null || bookCreateDTO.getPrice() == null) {
            throw new RuntimeException("Значения title, author, amount и price не могут быть null.");
        }

        if (bookCreateDTO.getPrice() != null) {
            bookCreateDTO.setPrice(bookCreateDTO.getPrice().setScale(2, RoundingMode.HALF_UP));
        }
        Book book = bookMapper.toEntity(bookCreateDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    /**
     * Обновляет существующую книгу по её идентификатору.
     * <p>
     * Обновляет все поля книги данными из DTO, включая название, автора,
     * количество и цену. Цена автоматически округляется до двух десятичных знаков.
     * Если цена равна {@code null}, она устанавливается как {@code null} в сущности.
     * </p>
     *
     * @param id            уникальный идентификатор обновляемой книги (не может быть {@code null})
     * @param bookCreateDTO DTO с новыми данными для книги (не может быть {@code null})
     * @return DTO обновленной книги
     * @throws RuntimeException если книга с указанным ID не найдена
     */
    @Transactional
    public BookDTO updateBook(Long id, BookCreateDTO bookCreateDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга с ID " + id + " не найдена"));

        existingBook.setTitle(bookCreateDTO.getTitle());
        existingBook.setAuthor(bookCreateDTO.getAuthor());
        existingBook.setAmount(bookCreateDTO.getAmount());
        if (bookCreateDTO.getPrice() != null) {
            existingBook.setPrice(bookCreateDTO.getPrice().setScale(2, RoundingMode.HALF_UP));
        } else {
            existingBook.setPrice(null);
        }
        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(updatedBook);
    }

    /**
     * Удаляет книгу по её уникальному идентификатору.
     *
     * @param bookId уникальный идентификатор удаляемой книги (не может быть {@code null})
     * @throws RuntimeException если книга с указанным ID не найдена
     */
    @Transactional
    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException("Книга с ID " + bookId + " не найдена");
        }
        bookRepository.deleteById(bookId);
    }

    /**
     * Возвращает общее количество записей о книгах в базе данных.
     *
     * @return количество записей о книгах (количество строк в таблице)
     */
    public Long countBook() {
        return bookRepository.count();
    }

    /**
     * Возвращает суммарное количество всех экземпляров книг.
     * <p>
     * В отличие от {@link #countBook()}, который возвращает количество записей,
     * этот метод суммирует значения поля {@code amount} для всех книг,
     * что дает общее количество экземпляров книг в наличии.
     * </p>
     *
     * @return общее количество экземпляров всех книг
     * @see #countBook()
     */
    public Long countAllBooks() {
        return bookRepository.findAll().stream()
                .mapToLong(Book::getAmount)
                .sum();
    }
}