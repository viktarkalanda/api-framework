# API Framework Project

## About

This project is an API testing automation framework.

## Test Data

The test data is located in the directory `src/test/resources/testdata`.

## Settings

The project settings are stored in the file `src/main/resources/settings.json`.



## Running Tests

To run the tests from the project, execute the following command:

mvn clean test -Dtestsuite=${testsuite}


Отчет о тестировании
Сводная информация
Всего выполнено тестов: 8
Успешно пройдено: 4
Завершено с ошибками: 4
Тесты, завершившиеся с ошибками
1. testCreatePlayerGenderValidation
   Статус: FAILED
   Описание: Проверка валидации поля "gender" при создании игрока
   Ошибка: Ошибка валидации поля "gender". Допустимые значения: "male" или "female".
2. testCreatePlayerPasswordValidation
   Статус: FAILED
   Описание: Проверка валидации поля "password" при создании игрока
   Ошибка: Ошибка валидации поля "password". Пароль должен содержать латинские буквы и цифры и иметь длину от 7 до 15 символов.
3. testCreatePlayerUniqueLogin
   Статус: FAILED
   Описание: Проверка уникальности поля "login" при создании игрока
   Ошибка: Ошибка валидации поля "login". Данный логин уже занят.
4. testCreatePlayerUniqueScreenName
   Статус: FAILED
   Описание: Проверка уникальности поля "screenName" при создании игрока
   Ошибка: Ошибка валидации поля "screenName". Данное имя пользователя уже занято.
   Примечания
   Ни в одном запросе с негативными данными не были получены сообщения об ошибках. Рекомендуется добавить проверку ответов на наличие сообщений об ошибках и обрабатывать их соответствующим образом.
   Обратите внимание, что для создания объекта используется метод GET. Рекомендуется использовать метод POST для создания объектов, чтобы соответствовать REST-принципам.
