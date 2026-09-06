package com.example.crudapp.repository;

import com.example.crudapp.model.User;                          //Импортируем наш класс User, чтобы интерфейс JpaRepository<User, Long> смог понять что такое User.
                                                                //JpaRepository<T,ID> - это обобщенный интерфейс, где нужно указать тип нашей сущности - User и тип индетификатора нашей сущности - Long.
import org.springframework.data.jpa.repository.JpaRepository;   //(Spring Data JPA)Импорт самого интерфейса JpaRepository.
import org.springframework.stereotype.Repository;               //(Spring Data JPA)Нужен для аннотации Repository.


@Repository //Говорит Spring: "этот компонент работает с БД". НО ВООБЩЕ ДЛЯ ИНТЕРФЕЙСОВ КОТОРЫЕ РАСШИРЯЮТ ИНТЕРФЕЙС JPAREPOSITORY
            //ЭТА АННОТАЦИЯ НЕОБЯЗАТЕЛЬНА. САМ ФАКТ РАСШИРЕНИЯ JPAREPOSITORY ГОВОРИТ НАМ О ТОМ ЧТО НАШ ИНТЕРФЕЙС USERREPOSITPRY РАБОТАЕТ С БД.
            //ЭТА АННОТАЦИЯ БУДЕТ ПОЛЕЗНА ТОЛЬКО В ТОМ СЛУЧАЕ КОГДА МЫ САМИ ПИШЕМ КОД.
            //Эта аннотация дает нам следующее:
            //1. Spring автоматически обнаружит этот интерфейс и создаст его объект, нам не нужно писать new.
            //2. Переводит ошибки БД в понятный для Java вид.
            //3.
public interface UserRepository extends JpaRepository<User, Long> { //JpaRepository - Spring Data JPA дает готовые
                                                                    // методы для CRUD проекта.
}

//Наследование интерфейса JpaRepository предоставляет доступ к таким CRUD-методам:
//save(User entity)           сохранить или обновить
//findById(Long id)           найти по ID
//findAll()                   найти всех
//deleteById(Long id)         удалить по ID
//delete(User entity)         удалить сущность
//count()                     количество записей
//existsById(Long id)         проверка существования
//И многое другое...