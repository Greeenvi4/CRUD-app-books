package com.example.crudapp.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация OpenAPI (Swagger) для документирования REST API приложения.
 *
 * <p>Данный класс настраивает мета-информацию о API, которая отображается
 * в Swagger UI и OpenAPI спецификации.</p>
 *
 * <p>Содержит следующую информацию:</p>
 * <ul>
 *     <li><b>Заголовок API:</b> {@code "My CRUD-books Application API"}</li>
 *     <li><b>Версия:</b> {@code 1.0.0}</li>
 *     <li><b>Описание:</b> Сервис управления складским учетом книг</li>
 *     <li><b>Контактные данные:</b> имя разработчика и email</li>
 *     <li><b>Лицензия:</b> Apache 2.0</li>
 *     <li><b>Сервер:</b> локальный сервер по адресу {@code http://localhost:8080}</li>
 * </ul>
 *
 * <p><b>Swagger UI доступен по адресу:</b></p>
 * <ul>
 *     <li>{@code http://localhost:8080/swagger-ui/index.html}</li>
 *     <li>{@code http://localhost:8080/v3/api-docs} — OpenAPI спецификация в формате JSON</li>
 * </ul>
 *
 * <p>Аннотация {@link OpenAPIDefinition} используется для централизованной
 * настройки документации всего API.</p>
 *
 * @see OpenAPIDefinition
 * @see Info
 * @see Contact
 * @see License
 * @see Server
 *
 * @author Andrus Gregory
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "My CRUD-books Application API",
                version = "1.0.0",
                description = "REST API для CRUD-приложения. 'Сервис управления складским учетом книг'",
                contact = @Contact(
                        name = "Andrus Gregory",
                        email = "grishavic@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://springdoc.org"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local server"
                )
        }
)
public class OpenAPIConfig {
}