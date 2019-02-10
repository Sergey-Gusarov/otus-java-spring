# otus-java-spring

# [Разработчик на Spring Framework](https://otus.ru/lessons/javaspring/)

Домашние задания по курсу Spring Framework

---
#### Занятие 2
#### [Программа по проведению тестирования](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw2/otus-hw-2)
В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопросов). 
Программа должна спросить у пользователя фамилию и имя, спросить 5 вопросов из CSV-файла и вывести результат тестирования. <br>
Ecosystem:
> - Spring Context
 
#### Занятие 4
#### [Annotation- + Java-based конфигурация приложения, i18, YML-настройки и тестирование](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw4/otus-hw-4)
Переписать конфигурацию в виде Java + Annotation-based конфигурации.
Локализовать выводимые сообщения и вопросы теста. Добавить файл настроек *.yml. Реализовать тестирование функционала.  <br>
Ecosystem:
> - Spring Context
> - Spring Boot
> - Spring Test

#### Занятие 5
#### [Spring Shell поддержка](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw5/otus-hw-5)
Перевести приложение для проведения опросов на Spring Shell. Проект создать через spring-initializer.
Реализовать banner и тесты.  <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Shell
 
#### Занятие 6
#### [Spring JDBC](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw6/otus-hw-6)
Создать приложение хранящее информацию о книгах в билиотеке. Использовать Spring JDBC и реляционную базу.
Использовать реляционную БД и встроенную in-memory. Предусмотреть таблицы авторов, книг и жанров. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring JDBC
> - H2
 
#### Занятие 8
#### [Spring JPA](#### [Spring JPA](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw8/otus-hw-8))
Переписать приложение для хранения книг с использованием ORM. Использовать JPA, Hibernate только в качестве JPA-провайдера.
Покрыть DAO тестами используя H2 базу данных и соответствующий H2 Hibernate-диалект. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Boot JDBC
> - Spring Data JPA
> - Spring Shell
> - H2
 
#### Занятие 10
#### [Spring Data JPA](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw10/otus-hw-10)
Реализовать весь функционал работы с БД в приложении для хранения книг с использованием DATA JPA репозиториев. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Data Jpa
> - Spring Shell
> - H2
 
#### Занятие 12
#### [Spring Data Mongodb](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw12/otus-hw-12)
Использовать MongoDB и spring-data для хранения информации о книгах.
Тесты реализовать с помощью embedded-mongodb. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Data Mongodb
> - Embedded Mongodb
> - Spring Shell
 
#### Занятие 14
#### [Spring Web MVC](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw14/otus-hw-14)
Разработать CRUD приложение с Web UI и хранением данных в БД.
Использовать классический View, предусмотреть страницу отображения всех сущностей и создания/редактирования.
View на Thymeleaf, classic Controllers. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Data Mongodb
> - Spring Web
> - Thymeleaf
> - Embedded Mongodb
> - Vanilla.js
  
#### Занятие 15
#### [Spring Web REST](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw15/otus-hw-15)
Разработать CRUD приложение с Web UI и хранением данных в БД.
Использовать AJAX и REST-контроллеры. Переписать приложение с классических View на AJAX архитектуру и REST-контроллеры.
<br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Data Mongodb
> - Spring Web
> - Thymeleaf
> - Lombok
> - Embedded Mongodb
> - Vanilla.js
 
#### Занятие 17
#### [Spring WebFlux](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw17/otus-hw-17)
Использовать WebFlux вместо классического embedded web-server. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Mongodb Reactive
> - Spring WebFlux
> - Lombok
> - Embedded Mongodb

 
#### Занятие 19
#### [Spring Security](https://github.com/SerPenTeHoK/otus-hw-19/pull/1)
В CRUD Web-приложение добавить мехнизм Form-based аутентификации.
Дополнительно реализовать UsersServices. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Mongodb
> - Spring Web
> - Spring Security


#### Занятие 21
#### [Spring Security ACL(в закрытом репозитарии)] 
Настроить в приложении авторизацию на уровне доменных сущностей. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Jpa
> - Spring Web
> - Spring Security
> - Spring Security Acl
 
### Занятие 23
#### [Spring Batch](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw23/otus-hw-23)
Разработать процедуру миграции данных из реляционного хранилища в NoSQL или наоборот
Используя Spring Batch. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Mongodb
> - Spring Data Jpa
> - Spring Web
> - Spring Batch
> - Spring Shell
> - H2
> - PostgreSql
 
#### Занятие 25
#### [Spring Integration](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw25/otus-hw-25)
Реализовать обработку доменной сущности через каналы Spring Integration. <br>
Ecosystem:
> - Spring Boot
> - Spring Integration
> - Spring Messaging


#### Занятие 27
#### [Spring Actuator](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw27/otus-hw-27)
Реализовать использование метрик healthchecks и logfile.<br>
Ecosystem:
> - Spring Boot
> - Spring Data Jpa
> - Spring Boot Actuator
> - Spring Web


#### Занятие 29
#### [Spring Boot + Docker](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw29/otus-hw-29)
Обернуть приложение и БД в docker-контейнер. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Jpa
> - Spring Web
> - Spring Data Rest
> - Mongo
> - Docker

#### Занятие 31
#### [Spring Boot + Docker](https://github.com/Sergey-Gusarov/otus-java-spring/tree/master/hw31/otus-hw-31)
Обернуть внешние вызовы в Hystrix. 
Обернуть все внешние вызовы в Hystrix, Hystrix Javanica. Опционально: Поднять Turbine Dashboard для мониторинга.
> - Spring Boot
> - Spring Data Mongodb
> - Spring Web
> - Spring Security
> - Hystrix