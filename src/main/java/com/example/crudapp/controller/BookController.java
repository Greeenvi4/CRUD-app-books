package com.example.crudapp.controller;

import com.example.crudapp.dto.BookCreateDTO;
import com.example.crudapp.dto.BookDTO;
import com.example.crudapp.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API для управления книгами в библиотеке")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

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

    @Operation(
            summary = "Получить книгу по ID",
            description = "Возвращает книгу по её уникальному идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Книга найдена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Книга не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"status\": 404, \"message\": \"Книга с ID 1 не найдена\", \"timestamp\": \"2026-09-06T10:00:00\"}"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID книги", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(
            summary = "Создать новую книгу",
            description = "Добавляет новую книгу в библиотеку"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Книга успешно создана"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные данные запроса",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Пустое название",
                                            value = "{\"status\": 400, \"message\": \"Название книги не может быть пустым\", \"timestamp\": \"2026-09-06T10:00:00\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Отрицательное количество",
                                            value = "{\"status\": 400, \"message\": \"Количество не может быть отрицательным\", \"timestamp\": \"2026-09-06T10:00:00\"}"
                                    )
                            }
                    )
            )
    })
    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @Parameter(description = "Данные для создания книги", required = true)
            @Valid @RequestBody BookCreateDTO bookCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookCreateDTO));
    }

    @Operation(
            summary = "Обновить книгу",
            description = "Обновляет существующую книгу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Книга успешно обновлена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Книга не найдена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные данные запроса"
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

    @Operation(
            summary = "Удалить книгу",
            description = "Удаляет книгу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Книга успешно удалена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Книга не найдена"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID книги для удаления", example = "1", required = true)
            @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получить количество книг",
            description = "Возвращает общее количество книг в библиотеке"
    )
    @GetMapping("/count")
    public ResponseEntity<String> countBook() {
        return ResponseEntity.ok("Количество записей: " + bookService.countBook());
    }

    @Operation(
            summary = "Получить общее количество экземпляров",
            description = "Возвращает общее количество всех экземпляров книг"
    )
    @GetMapping("/countall")
    public ResponseEntity<String> countAllBook() {
        return ResponseEntity.ok("Количество всех экземпляров книг: " + bookService.countAllBooks());
    }
}