package com.example.crudapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания или обновления книги")
public class BookCreateDTO {
    @Schema(
            description = "Название книги",
            example = "Война и мир",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Название книги не может быть пустым")
    private String title;

    @Schema(
            description = "Автор книги",
            example = "Лев Толстой",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Автор не может быть пустым")
    private String author;

    @Schema(
            description = "Количество экземпляров книги",
            example = "5",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Количество не может быть пустым")
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Integer amount;

    @Schema(
            description = "Цена книги в рублях",
            example = "500",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Integer price;
}