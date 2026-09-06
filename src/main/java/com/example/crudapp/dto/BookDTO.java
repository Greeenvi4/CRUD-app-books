package com.example.crudapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для отображения информации о книге")
public class BookDTO {

    @Schema(
            description = "Уникальный идентификатор книги",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Название книги",
            example = "Война и мир",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Автор книги",
            example = "Лев Толстой",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String author;

    @Schema(
            description = "Количество экземпляров книги",
            example = "5",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer amount;

    @Schema(
            description = "Цена книги в рублях",
            example = "500",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer price;
}