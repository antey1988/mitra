# mitra

### Сборка (Maven Apache)
- mvn package

### Запуск и основка контейнеров(Docker)
- docker-compose -f docker-compose.yml up -d
- docker-compose -f docker-compose.yml -p mitra stop app_client app_server postgres

### Использование
#### Получение списка сообщений, в том числе с фильтрацие по дате
- request: GET http://localhost:8080/messages
- params: begin (YYYY-MM-ddTHH:mm:ss), end (YYYY-MM-ddTHH:mm:ss)
- response: [
  {"id": 1, "content": "Раз", "createAt": "2022-03-22T00:00:00"},
  {"id": 2, "content": "Два", "createAt": "2022-03-22T12:00:00"}]
#### Получение сообщения по идентификатору
- request: GET http://localhost:8080/messages/{id}"
- response: {"id": 1, "content": "Раз", "createAt": "2022-03-22T00:00:00"}
#### Создание сообщения
- request: POST http://localhost:8080/messages"
- request body: {"content": "text", "createAt": "YYYY-MM-ddTHH:mm:ss"}, createAt - не обязательный