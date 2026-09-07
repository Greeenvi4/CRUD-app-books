package com.example.crudapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO для передачи информации о книге между слоями приложения и отображения информации клиенту.
 *
 * <p>Данный класс используется для:
 * <ul>
 *   <li>Передачи данных о книге через REST API</li>
 *   <li>Отображения сущности {@link com.example.crudapp.model.Book} в формат, удобный для клиентов</li>
 *   <li>Документирования API с помощью OpenAPI/Swagger</li>
 * </ul>
 * </p>
 *
 * <p>Использует Lombok для автоматической генерации:
 * <ul>
 *   <li>{@code @Data} - геттеры, сеттеры, {@code toString()}, {@code equals()} и {@code hashCode()}</li>
 *   <li>{@code @NoArgsConstructor} - конструктор без параметров</li>
 *   <li>{@code @AllArgsConstructor} - конструктор со всеми параметрами</li>
 * </ul>
 * </p>
 *
 * <p><b>Пример использования в контроллере:</b>
 * <pre>
 * {@code @GetMapping("/{id}")
 * public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
 *     Book book = bookService.findById(id);
 *     return ResponseEntity.ok(bookMapper.toDTO(book));
 * }}
 * </pre>
 * </p>
 *
 * @see com.example.crudapp.model.Book
 * @see io.swagger.v3.oas.annotations.media.Schema
 * @author Andrus Gregory
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для отображения информации о книге")
public class BookDTO {

    /**
     * Уникальный идентификатор книги в системе.
     *
     * <p>Это поле доступно только для чтения (READ_ONLY), так как идентификатор
     * генерируется автоматически базой данных при создании новой книги.
     * Клиент не должен передавать это значение при создании или обновлении книги.</p>
     *
     * <p><b>Пример значения:</b> {@code 1}</p>
     *
     * @see Schema.AccessMode#READ_ONLY
     */
    @Schema(
            description = "Уникальный идентификатор книги",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    /**
     * Название книги.
     *
     * <p>Обязательное поле при создании и обновлении книги.
     * Не может быть {@code null} или пустым.</p>
     *
     * <p><b>Пример значения:</b> {@code "Война и мир"}</p>
     *
     * @see Schema.RequiredMode#REQUIRED
     */
    @Schema(
            description = "Название книги",
            example = "Война и мир",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    /**
     * Автор(ы) книги.
     *
     * <p>Обязательное поле при создании и обновлении книги.
     * Не может быть {@code null} или пустым.</p>
     *
     * <p>Может содержать одного автора или нескольких через запятую.</p>
     *
     * <p><b>Пример значения:</b> {@code "Лев Толстой"}</p>
     *
     * @see Schema.RequiredMode#REQUIRED
     */
    @Schema(
            description = "Автор книги",
            example = "Лев Толстой",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String author;

    /**
     * Количество доступных экземпляров книги.
     *
     * <p>Обязательное поле при создании и обновлении книги.
     * Не может быть {@code null}.</p>
     *
     * <p>Значение должно быть неотрицательным числом (минимальное значение - 0).
     * Используется для отслеживания наличия книги на складе.</p>
     *
     * <p><b>Пример значения:</b> {@code 5}</p>
     *
     * @see Schema.RequiredMode#REQUIRED
     */
    @Schema(
            description = "Количество экземпляров книги",
            example = "5",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer amount;

    /**
     * Цена книги в рублях.
     *
     * <p>Обязательное поле. Не может быть {@code null}.</p>
     *
     * <p>Значение должно быть неотрицательным числом.
     * Минимальное значение — {@code 0}.</p>
     *
     * <p>Максимальная точность: до 10 целых знаков и 2 знака после запятой.
     * Например: {@code 500.00}, {@code 1234567890.99}.</p>
     *
     * <p>Используется для указания стоимости книги для покупателей.</p>
     *
     * @see Schema.RequiredMode#REQUIRED
     */
    @Schema(
            description = "Цена книги в рублях",
            example = "500.00",
            minimum = "0.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Digits(integer = 10, fraction = 2, message = "Цена должна иметь не более 2 знаков после запятой")
    private BigDecimal price;
}