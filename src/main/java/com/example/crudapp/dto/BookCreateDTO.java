package com.example.crudapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * DTO для создания или обновления информации о книге.
 *
 * <p>Используется при {@code POST} и {@code PUT} запросах для передачи данных книги
 * от клиента к серверу.</p>
 *
 * <p>Все поля обязательны для заполнения и проходят валидацию перед сохранением
 * в базу данных.</p>
 *
 * <p>Особенности валидации:</p>
 * <ul>
 *     <li>{@code title} и {@code author} — не могут быть пустыми или состоять только из пробелов</li>
 *     <li>{@code amount} — обязательное целое число, не менее {@code 0}</li>
 *     <li>{@code price} — обязательное число, не менее {@code 0}, автоматически округляется до 2 знаков</li>
 * </ul>
 *
 * @see com.example.crudapp.model.Book
 * @see BookDTO
 * @author Andrus Gregory
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания или обновления книги")
public class BookCreateDTO {

    /**
     * Название книги.
     *
     * <p>Обязательное поле. Не может быть {@code null}, пустым или состоять только из пробелов.</p>
     *
     * <p><b>Примеры:</b> {@code "Война и мир"}, {@code "Мастер и Маргарита"}</p>
     */
    @Schema(
            description = "Название книги",
            example = "Война и мир",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Название книги не может быть пустым")
    private String title;

    /**
     * Автор книги.
     *
     * <p>Обязательное поле. Не может быть {@code null}, пустым или состоять только из пробелов.</p>
     *
     * <p><b>Примеры:</b> {@code "Лев Толстой"}, {@code "Фёдор Достоевский"}</p>
     */
    @Schema(
            description = "Автор книги",
            example = "Лев Толстой",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Автор не может быть пустым")
    private String author;

    /**
     * Количество экземпляров книги в наличии.
     *
     * <p>Обязательное поле. Не может быть {@code null}.</p>
     *
     * <p>Значение должно быть неотрицательным целым числом.
     * Минимальное значение — {@code 0} (книга отсутствует на складе).</p>
     *
     * <p><b>Примеры:</b> {@code 0}, {@code 5}, {@code 100}</p>
     */
    @Schema(
            description = "Количество экземпляров книги",
            example = "5",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Количество не может быть пустым")
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Integer amount;

    /**
     * Цена книги в рублях.
     *
     * <p>Обязательное поле. Не может быть {@code null}.</p>
     *
     * <p>Значение должно быть неотрицательным числом.
     * Минимальное значение — {@code 0} (бесплатная книга).</p>
     *
     * <p>При установке значения через {@link #setPrice(BigDecimal)} автоматически
     * выполняется округление до 2 знаков после запятой с использованием
     * {@link RoundingMode#HALF_UP} (стандартное математическое округление).</p>
     *
     * <p><b>Примеры:</b> {@code 500.00}, {@code 0.00}, {@code 999.99}</p>
     *
     * @see #setPrice(BigDecimal)
     */
    @Schema(
            description = "Цена книги в рублях",
            example = "500.00",
            minimum = "0.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 0, message = "Цена не может быть отрицательной")
    private BigDecimal price;

    /**
     * Устанавливает цену книги с автоматическим округлением до 2 знаков после запятой.
     *
     * <p>Округление выполняется по правилу {@link RoundingMode#HALF_UP}
     * (математическое округление: {@code 500.005} → {@code 500.01}).</p>
     *
     * <p>Если передан {@code null}, то цена устанавливается в {@code null}.</p>
     *
     * @param price новая цена книги в рублях (может быть {@code null})
     *
     * @see BigDecimal#setScale(int, RoundingMode)
     */
    public void setPrice(BigDecimal price) {
        if (price != null) {
            this.price = price.setScale(2, RoundingMode.HALF_UP);
        } else {
            this.price = null;
        }
    }
}