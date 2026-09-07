package com.example.crudapp.controller;

import com.example.crudapp.dto.BookCreateDTO;
import com.example.crudapp.dto.BookDTO;
import com.example.crudapp.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления операциями с книгами.
 * <p>
 * Предоставляет RESTful API endpoints для выполнения CRUD операций над книгами
 * в библиотечной системе. Все endpoints используют базовый путь {@code /api/books}.
 * </p>
 *
 * <p>Контроллер поддерживает следующие операции:</p>
 * <ul>
 *     <li>Получение списка всех книг (GET /api/books)</li>
 *     <li>Получение книги по ID (GET /api/books/{id})</li>
 *     <li>Создание новой книги (POST /api/books)</li>
 *     <li>Обновление существующей книги (PUT /api/books/{id})</li>
 *     <li>Удаление книги (DELETE /api/books/{id})</li>
 *     <li>Получение количества книг (GET /api/books/count)</li>
 *     <li>Получение общего количества экземпляров (GET /api/books/countall)</li>
 * </ul>
 *
 * @author Andrus Gregory
 * @see BookService
 * @see BookDTO
 * @see BookCreateDTO
 */
@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API для управления книгами в библиотеке")
public class BookController {

    private final BookService bookService;

    /**
     * Конструктор для внедрения зависимости сервиса книг.
     *
     * @param bookService сервис для выполнения бизнес-логики операций с книгами
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Получает список всех книг в библиотеке.
     * <p>
     * Метод возвращает все записи книг, преобразованные в DTO объекты.
     * Список сортируется в порядке сохранения в базе данных.
     * </p>
     *
     * @return {@link ResponseEntity} со списком {@link BookDTO} и статусом HTTP 200 (OK)
     * @see BookDTO
     */
    @Operation(
            summary = "Получить все книги",
            description = "Возвращает список всех книг в библиотеке"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно получен список книг",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * Получает книгу по её уникальному идентификатору.
     *
     * @param id уникальный идентификатор книги (не может быть {@code null})
     * @return {@link ResponseEntity} с {@link BookDTO} и статусом HTTP 200 (OK)
     * @throws RuntimeException если книга с указанным ID не найдена
     */
    @Operation(
            summary = "Получить книгу по ID",
            description = "Возвращает книгу по её уникальному идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Книга найдена"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID книги", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /**
     * Создает новую книгу в библиотеке.
     * <p>
     * Принимает данные книги в формате JSON и создает новую запись.
     * Все обязательные поля должны быть заполнены и пройти валидацию.
     * </p>
     *
     * @param bookCreateDTO DTO с данными для создания книги (должен быть валидным)
     * @return {@link ResponseEntity} с созданным {@link BookDTO} и статусом HTTP 201 (CREATED)
     * @throws RuntimeException если данные не прошли валидацию
     * @see BookCreateDTO
     */
    @Operation(
            summary = "Создать новую книгу",
            description = "Добавляет новую книгу в библиотеку"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Книга успешно создана"
            )
    })
    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @Parameter(description = "Данные для создания книги", required = true)
            @Valid @RequestBody BookCreateDTO bookCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookCreateDTO));
    }


    /**
     * Обновляет существующую книгу по её идентификатору.
     * <p>
     * Полностью заменяет данные книги на новые из запроса.
     * Если книга не найдена, возвращается ошибка.
     * </p>
     *
     * @param id            уникальный идентификатор обновляемой книги
     * @param bookCreateDTO DTO с обновленными данными книги (должен быть валидным)
     * @return {@link ResponseEntity} с обновленным {@link BookDTO} и статусом HTTP 200 (OK)
     * @throws RuntimeException если книга не найдена или данные не валидны
     */
    @Operation(
            summary = "Обновить книгу",
            description = "Обновляет существующую книгу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Книга успешно обновлена"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @Parameter(description = "ID книги для обновления", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Обновленные данные книги", required = true)
            @Valid @RequestBody BookCreateDTO bookCreateDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookCreateDTO));
    }

    /**
     * Удаляет книгу по её уникальному идентификатору.
     * <p>
     * Безвозвратно удаляет запись книги из базы данных.
     * При успешном удалении возвращает статус HTTP 204 (No Content).
     * </p>
     *
     * @param id уникальный идентификатор удаляемой книги
     * @return {@link ResponseEntity} со статусом HTTP 204 (NO_CONTENT)
     * @throws RuntimeException если книга с указанным ID не найдена
     */
    @Operation(
            summary = "Удалить книгу",
            description = "Удаляет книгу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Книга успешно удалена"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID книги для удаления", example = "1", required = true)
            @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получает общее количество записей книг в базе данных.
     * <p>
     * Возвращает количество строк в таблице книг, а не общее количество экземпляров.
     * </p>
     *
     * @return {@link ResponseEntity} с текстовым сообщением, содержащим количество записей,
     *         и статусом HTTP 200 (OK)
     */
    @Operation(
            summary = "Получить количество книг",
            description = "Возвращает общее количество книг в библиотеке"
    )
    @GetMapping("/count")
    public ResponseEntity<String> countBook() {
        return ResponseEntity.ok("Количество записей: " + bookService.countBook());
    }

    /**
     * Получает общее количество всех экземпляров книг.
     * <p>
     * Суммирует значения поля {@code amount} для всех книг, возвращая общее
     * количество экземпляров в наличии. В отличие от {@link #countBook()},
     * который возвращает количество уникальных записей.
     * </p>
     *
     * @return {@link ResponseEntity} с текстовым сообщением, содержащим общее количество
     *         экземпляров, и статусом HTTP 200 (OK)
     * @see #countBook()
     */
    @Operation(
            summary = "Получить общее количество экземпляров",
            description = "Возвращает общее количество всех экземпляров книг"
    )
    @GetMapping("/countall")
    public ResponseEntity<String> countAllBook() {
        return ResponseEntity.ok("Количество всех экземпляров книг: " + bookService.countAllBooks());
    }
}