package com.example.crudapp.controller;

import com.example.crudapp.model.User;              //Импортируем класс User (шаблон сущности (таблицы))
import com.example.crudapp.service.UserService;     //Импортируем класс UserService (бизнес-логика нашего приложения)
import org.springframework.web.bind.annotation.*;   //Необходимо для аннотаций, которые обрабатывают HTTP-запросы (GET,POST,PUT,DELETE) (@RequestMapping,@GetMapping,@PostMapping,@PutMapping,@DeleteMapping,@PathVariable,@RequestBody)

import java.util.List;                              //Для метода getAllUsers (получить полный список пользователей из БД).

@RestController                         //Говорит Spring - этот класс обрабатывает HTTP-запросы и возвращает JSON (JSON - это формат который понятен клиентам (браузеру, Postman и т.д.)).
@RequestMapping("/api/users")        //Эта аннотация необходима для маршрутизации (mapping) входящих HTTP-запросов на конкертные методы контроллера.
                                        //Иными словами эта аннотация определяет какому URL-адресу соотвествует тот или иной метод контроллера.
                                        // /api/users - это базовый URL для всех методов.
public class UserController {

    private final UserService userService;                                               //Для внедрения зависимостей (DI).

    public UserController(UserService userService) {
        this.userService = userService;
    }   //Для внедрения зависимостей.

    // GET /api/users - получить всех пользователей
    @GetMapping                         //Обрабатывает HTTP GET запросы.
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/{id} - получить пользователя по ID
    @GetMapping("/{id}")                //Обрабатывает HTTP GET запросы. Но уже по конкретному ID пользователя. {id} - это динамическая часть пути, которая может изменяться.
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // POST /api/users - создать нового пользователя
    @PostMapping                        //Обрабатывает HTTP POST запросы.
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT /api/users/{id} - обновить пользователя
    @PutMapping("/{id}")                //Обрабатывает HTTP PUT запросы. По конкретному ID. {id} - это динамическая часть пути, которая может изменяться.
    public User updateUser(@PathVariable Long id, @RequestBody User user) { //@PathVariable извлекает динамическу часть из URL - в нашем случае это id.
        return userService.updateUser(id, user);                            //@RequestBody преобразует JSON из тела запроса в Java-объект.
    }

    // DELETE /api/users/{id} - удалить пользователя //Обрабатывает HTTP DELETE запросы.
    @DeleteMapping("/{id}") //{id} - это динамическая часть пути, которая может изменяться.
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

//                                        Как работают endpoint'ы
//          HTTP метод	URL	            Что делает	                    Пример тела запроса
//            GET	    /api/users	    Получить всех	                -
//            GET	    /api/users/1	Получить пользователя с ID=1	-
//            POST	    /api/users	    Создать пользователя	        {"name": "Иван", "email": "ivan@mail.com"}
//            PUT	    /api/users/1	Обновить пользователя с ID=1	{"name": "Пётр", "email": "petr@mail.com"}
//            DELETE	/api/users/1	Удалить пользователя с ID=1	    -