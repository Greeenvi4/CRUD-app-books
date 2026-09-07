package com.example.crudapp.mapping;

import com.example.crudapp.dto.BookCreateDTO;
import com.example.crudapp.dto.BookDTO;
import com.example.crudapp.model.Book;
import org.springframework.stereotype.Component;

/**
 * Маппер для преобразования между сущностью {@link Book} и DTO-объектами.
 *
 * <p>Отвечает за преобразование данных между слоями приложения:
 * <ul>
 *     <li>Из {@link Book} (сущность БД) в {@link BookDTO} (ответ клиенту)</li>
 *     <li>Из {@link BookCreateDTO} (запрос от клиента) в {@link Book} (сущность БД)</li>
 * </ul>
 * </p>
 *
 * <p>Все методы безопасны для передачи {@code null} — при получении {@code null}
 * возвращают {@code null} без выбрасывания исключений.</p>
 *
 * <p>Компонент Spring, автоматически обнаруживается через {@link Component}.</p>
 *
 * @see Book
 * @see BookDTO
 * @see BookCreateDTO
 * @author Andrus Gregory
 */
@Component
public class BookMapper {

    /**
     * Преобразует сущность {@link Book} в DTO {@link BookDTO} для отправки клиенту.
     *
     * <p>Выполняет прямое маппинг всех полей без изменений данных.</p>
     *
     * <p><b>Поля, которые копируются:</b></p>
     * <ul>
     *     <li>{@code book_id} — идентификатор книги</li>
     *     <li>{@code title} — название</li>
     *     <li>{@code author} — автор</li>
     *     <li>{@code amount} — количество экземпляров</li>
     *     <li>{@code price} — цена</li>
     * </ul>
     *
     * <p><b>Пример использования:</b></p>
     * <pre>{@code
     * Book book = bookRepository.findById(1L);
     * BookDTO dto = bookMapper.toDto(book);
     * }</pre>
     *
     * @param book сущность книги из базы данных (может быть {@code null})
     * @return DTO с данными книги, или {@code null}, если передан {@code null}
     */
    public BookDTO toDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookDTO(
                book.getBook_id(),
                book.getTitle(),
                book.getAuthor(),
                book.getAmount(),
                book.getPrice()
        );
    }

    /**
     * Преобразует DTO {@link BookCreateDTO} в сущность {@link Book} для сохранения в базу данных.
     *
     * <p>Создаёт новый экземпляр {@link Book} и копирует все поля из DTO.
     * Поле {@code book_id} не устанавливается (генерируется базой данных при сохранении).</p>
     *
     * <p><b>Поля, которые копируются:</b></p>
     * <ul>
     *     <li>{@code title} — название</li>
     *     <li>{@code author} — автор</li>
     *     <li>{@code amount} — количество экземпляров</li>
     *     <li>{@code price} — цена (уже отформатирована до 2 знаков в DTO)</li>
     * </ul>
     *
     * <p><b>Пример использования:</b></p>
     * <pre>{@code
     * BookCreateDTO dto = new BookCreateDTO("Война и мир", "Лев Толстой", 5, new BigDecimal("500.00"));
     * Book book = bookMapper.toEntity(dto);
     * bookRepository.save(book);
     * }</pre>
     *
     * @param bookCreateDTO DTO с данными для создания или обновления книги (может быть {@code null})
     * @return новая сущность {@link Book}, или {@code null}, если передан {@code null}
     *
     * @see BookCreateDTO
     */
    public Book toEntity(BookCreateDTO bookCreateDTO) {
        if (bookCreateDTO == null) {
            return null;
        }
        Book book = new Book();
        book.setTitle(bookCreateDTO.getTitle());
        book.setAuthor(bookCreateDTO.getAuthor());
        book.setAmount(bookCreateDTO.getAmount());
        book.setPrice(bookCreateDTO.getPrice());
        return book;
    }
}