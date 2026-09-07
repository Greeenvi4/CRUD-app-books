package com.example.crudapp.repository;

import com.example.crudapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Book}.
 *
 * <p>Предоставляет стандартный набор методов CRUD (создание, чтение, обновление, удаление)
 * для управления книгами в базе данных.</p>
 *
 * <p>Наследует функциональность {@link JpaRepository}, что даёт:</p>
 * <ul>
 *     <li>Базовые CRUD-операции: {@code save()}, {@code findById()}, {@code findAll()}, {@code deleteById()}</li>
 *     <li>Постраничную выборку: {@code findAll(Pageable)}</li>
 *     <li>Сортировку: {@code findAll(Sort)}</li>
 *     <li>Проверку существования: {@code existsById()}</li>
 *     <li>Подсчёт записей: {@code count()}</li>
 * </ul>
 *
 * <p>При необходимости можно добавлять пользовательские методы запросов:
 * <pre>{@code
 * // Пример пользовательского метода
 * List<Book> findByAuthor(String author);
 * }</pre>
 * </p>
 *
 * <p>Использует {@code Long} в качестве типа идентификатора сущности {@link Book}.</p>
 *
 * @see Book
 * @see JpaRepository
 * @see org.springframework.data.repository.CrudRepository
 * @author Andrus Gregory
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
