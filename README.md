# Инструкция по запуску авто-тестов.

## Приложения, необходимые для запуска: ##
* IDEA
* NodeJS
* Docker
* Браузер Google Chrome

## Шаги для запуска: ##
1. Создать Gradle проект.
1. В терминале IDEA c помощью команды git init создать локальный репозиторий.
1. С помощью команды git clone https://github.com/KiraMatsenko/qa-diploma.git клонировать удаленный.
1. Запустить Docker Desktop.
1. Ввести команду docker-compose up для запуска контейнеров.
1. Ввести команду cd gate-simulator для перехода в папку gate-simulator.
1. Ввести команду npm start --port 9999 ля запуска симулятора банковского шлюза.
1. Вернуться в корневой каталог проекта.
1. С помощью команды java -jar aqa-shop.jar запустить SUT.
1. Запустить тесты с помощью команды ./gradlew test.

### Запуск приложения с СУБД PostgreSQL: ###
1. Вводим в терминале команду Java -jar aqa-shop.jar --spring.profiles.active=post.
1. Для запуска тестов в терминале вводим команду ./gradlew test -D db.url=jdbc:postgresql://localhost:5432/app.