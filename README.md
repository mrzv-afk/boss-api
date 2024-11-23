# Boss API

Плагин для создания и управления боссами с продвинутыми возможностями.

## Описание

Boss API - это мощный инструмент для создания и управления боссами. Плагин предоставляет широкие возможности для настройки поведения боссов, их характеристик и спец эффектов.

## Основные возможности

- Создание кастомных боссов
- Управление характеристиками боссов (здоровье, урон и т.д.)
- Система голограмм для отображения информации
- Продвинутая система команд
- Гибкая настройка через конфигурационные файлы

## Требования

- Java 17
- Bukkit/Spigot/Paper сервер
- ProtocolLib

## Зависимости

- ProtocolLib
- JDBI
- Lombok
- Caffeine Cache
- LiteCommands

## Установка

1. Скачайте последнюю версию плагина
2. Поместите JAR файл в папку plugins вашего сервера
3. Перезапустите сервер
4. Настройте конфигурационные файлы по необходимости

## Использование

### Основные команды

- `/spawnboss [тип]` - Создать босса определенного типа
- `/spawnentity [тип]` - Создать сущность определенного типа

### Права доступа

- `boss.spawn` - Право на создание боссов
- `boss.entity.spawn` - Право на создание сущностей

## Конфигурация

Плагин использует несколько конфигурационных файлов:
- `config.yml` - Основные настройки
- `bosses.yml` - Настройки боссов
- `messages.yml` - Настройки сообщений

## Разработка

Проект использует Gradle для сборки. Для начала разработки:

1. Клонируйте репозиторий
2. Откройте проект в вашей IDE
3. Выполните `./gradlew build` для сборки проекта

## Структура проекта

- `boss-api` - Основное API для работы с боссами
- `boss-plugin` - Реализация плагина

## Лицензия

MIT License
