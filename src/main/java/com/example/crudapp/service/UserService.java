package com.example.crudapp.service;

import com.example.crudapp.model.User;                              //Импорт шаблона нашей сущности таблицы (класс User)
import com.example.crudapp.repository.UserRepository;               //Импорт нашего репозитория (интерфейс UserRepository)
import org.springframework.stereotype.Service;                      //(Автоматически приходит в Spring Boot из Spring)Для аннотации @Service
import org.springframework.transaction.annotation.Transactional;    //(Автоматически приходит в Spring Boot из Spring)Для аннотации @Transactional

import java.util.List;                                              //Для метода getAllUsers, который возвращает список всех пользователей из таблицы Users.

@Service                        //Говорит Spring что этот класс содержит бизнес-логику. Отделяет логику работы от контроллеров и доступа к данным.
@Transactional(readOnly = true) //Эта аннотация указывает на то, что все методы в этом классе являются транзакционными (также эта аннотация может применятся отдельно для каждого метода)
                                //Простыми словами - все операции в этом методе должны выполниться как одна неделимая часть. Если что-то подйет не так, то вернуть все в исходное состояние.
                                // readOnly = true -Все методы по умолчанию только для чтения.
public class UserService {

    private final UserRepository userRepository;        //Внедрение зависимости через конструктор (DI).

    public UserService(UserRepository userRepository) { //Внедрение зависимости через конструктор (DI).
        this.userRepository = userRepository;           //Для бизнес-логики используем методы определенные в интерфейсе UserRepository.
    }

    // Получить всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получить пользователя по ID. Если пользователь с таким ID не найден, то выбрасывается исключение.
    public User getUserById(Long users_id) {
        return userRepository.findById(users_id) //На самом деле этот метод findById() (как и все остальные методы из JpaRepository) возвращает не User, а Optional<User>, чтобы предотвратить появления ошибки NullPointerException.
                .orElseThrow(() -> new RuntimeException("Пользователь с ID " + users_id + " не найден")); //Метод orElseThrow() это метод из класса Optional, который в случае если значение остутсвует (null), выбрасывает созданное нами исключение.
                //() -> new RuntimeException("Пользователь с ID " + users_id + " не найден") - это лямбда выражение, которое переопределяет следующий метод из класса Supplier<RuntimeException>:
                //@Override
                //public RuntimeException get(){
                //  return new RuntimeException("Пользователь с ID " + users_id + " не найден");
                //}
    }

    // Создать нового пользователя
    @Transactional //Теперь это равносильно @Transactional(readOnly = false). Теперь мы можем вносить изменения в БД с помощью данного метода.
    public User createUser(User user) {
        // Проверка: email не должен быть null и не должен состоять их "пробелов"
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new RuntimeException("Email не может быть пустым");
        }
        return userRepository.save(user);
    }

    // Обновить пользователя
    @Transactional //Теперь это равносильно @Transactional(readOnly = false). Теперь мы можем вносить изменения в БД с помощью данного метода.
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        existingUser.setName(updatedUser.getName());  //Это как раз те самые геттеры/сеттерры которые создались автоматически с помощью Lombok в классе User.
        existingUser.setEmail(updatedUser.getEmail());

        return userRepository.save(existingUser);
    }

    // Удалить пользователя. Если пользователя с таким ID нет, то выбрасывается исключение.
    @Transactional  //Теперь это равносильно @Transactional(readOnly = false). Теперь мы можем вносить изменения в БД с помощью данного метода.
    public void deleteUser(Long users_id) {
        if (!userRepository.existsById(users_id)) {
            throw new RuntimeException("Пользователь с ID " + users_id + " не найден");
        }
        userRepository.deleteById(users_id);
    }
}