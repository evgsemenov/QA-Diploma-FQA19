 [![master](https://github.com/evgsemenov/QA-Diploma-FQA19/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/evgsemenov/QA-Diploma-FQA19/actions/workflows/gradle-publish.yml)
# Дипломная работа профессии "Тестировщик ПО"

### Данная работа представляет собой проект по автоматизированному тестированию web-приложения для покупки туристического тура.

Для запуска проекта необходим установленный на машину пользователя **[Docker](https://www.docker.com/)**.

По умолчанию проект настроен на подключение к PostgreSQL.

### **Инструкция по запуску:**

 1. Склонировать репозиторий

 2. Запустить базы данных и мок банковского сервиса командой `docker-compose up -d`
    
 3. Запустить целевое приложение консольной командой:
- для интеграции с PostgreSQL:
  
  `java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/db -jar artifacts/aqa-shop.jar`
  
- для интеграции с MySQL:
  
  `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/db -jar artifacts/aqa-shop.jar`


### **Запуск тестов:**

- При интеграции с PostgreSQL: `gradlew clean test`
- При интеграции с MySQL: `gradlew -DdbUrl=jdbc:mysql://localhost:3306/db clean test`

По завершении тестового прогона можно открыть отчеты Allure командой 

`gradlew allureServe`


Также можно сгенерировать файлы отчетов командой 

`gradlew allureReport` (файлы будут помещены в каталог ./build/reports/allure-report)

В каталоге ./build/reports/tests/test находятся отчеты, сгенерированные Gradle.


Свертывание контейнеров Docker осуществляется командой 

`docker-compose down`

### **Ссылки на документацию и отчеты:**

[План автоматизации тестирования](https://github.com/evgsemenov/QA-Diploma-FQA19/blob/master/test-documentation/Plan.md)

[Отчет о проведенном тестировании](https://github.com/evgsemenov/QA-Diploma-FQA19/blob/master/test-documentation/Report.md)

[Отчет о проведенной автоматизации](https://github.com/evgsemenov/QA-Diploma-FQA19/blob/master/test-documentation/Summary.md)

[Список выявленных дефектов](https://github.com/evgsemenov/QA-Diploma-FQA19/issues) 



  



