# FitLife Coach

**FitLife Coach** — модульное Android-приложение на основе Jetpack Compose и архитектурного паттерна MVI.  
Позволяет:
- Авторизоваться (фейковый сервер)
- Просматривать каталог тренировок и детальную карточку
- Управлять напоминаниями о тренировках
- Планировать питание: каталог рецептов и дневник калорий

---

## 📁 Структура проекта
- app/ — Application-модуль (NavHost, MainActivity)
- core/ — Общие зависимости и утилиты (MVI, Retrofit, Room, Koin)
- feature-auth/ — Фича авторизации (MVI, FakeAuthRepository)
- feature-workouts/ — Фича тренировок (MVI, FakeWorkoutsRepository)
- feature-nutrition/ — Фича питания (MVI, FakeNutritionRepository)

## 🔧 Требования

- Android Studio Flamingo или новее
- Kotlin 1.8+
- Android Gradle Plugin 7.4+
- Java 11

---

## 🚀 Быстрый старт

1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/your-org/fitlife-coach.git

## 🏗 Архитектура
MVI (Model-View-Intent) в каждом feature:

Intent — события от UI

Action — внутренние события VM

State — состояние экрана

Reducer — преобразование Action → State

BaseMviViewModel из core

DI с Koin:

Модули: coreModule, appModule, authModule, workoutsModule, nutritionModule

Корутин-диспетчеры IO, Default, Main в appModule

Navigation:

authGraph, workoutsGraph, nutritionGraph

Все экраны объединены в NavHost в MainActivity

## 📦 Fake-репозитории
Для работы без бэка используются фейковые реализации:

FakeAuthRepository — любой логин/регистрация возвращает fake-token…

FakeWorkoutsRepository — тестовый список тренировок

FakeNutritionRepository — каталог рецептов и примерная запись дневника


## 🔮 Дальнейшие улучшения
- Подключить реальный бэкенд (Retrofit + TokenStorage)

- Биометрия в Auth

- WorkManager-напоминания о тренировках

- MPAndroidChart для графиков калорий

- BottomNavigation для быстрого переключения модулей

- Юнит- и интеграционные тесты

