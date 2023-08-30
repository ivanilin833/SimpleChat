# Курсовой проект "Сетевой чат"
## Описание проекта

Было разработано два приложения для обмена текстовыми сообщениями по сети с помощью консоли между двумя и более пользователями. 

**Первое приложение - сервер чата**

**Второе приложение - клиент чата**

## Клиент

- Выбор имени для участия в чате;
- Читает настройки приложения из файла настроек "client.properties" ;
- Для выхода из чата нужно набрать команду выхода - “/exit”;
- Каждое сообщение участников записывается в текстовый файл "client.log".
## Cервер

- Установка порта для подключения клиентов через файл настроек "server.properties";
- Возможность подключиться к серверу в любой момент и присоединиться к чату;
- Запись всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки в файл логирования "server.log".