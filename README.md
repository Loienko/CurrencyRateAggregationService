# CurrencyRateAggregationService

Сервис агрегации курсов валют

1. Приложение должно иметь возможность загружать курсы валют из текстовых
файлов, которые предоставляют банки. Каждый банк предоставляет один файл
со всеми курсами валют.
2. Файлы могут быть трех форматов: CSV, XML, JSON. Имя файла (до расширения)
является названием банка и должно далее использоваться системой в этом
виде.
3. Каждый файл содержит следующую информацию:
Код валюты Покупка Продажа
USD 25.90 26.10
EUR 30.00 31.00
CHF 26.00 -
... ... ...
4. Список валют может быть специфичен для каждого банка. Например, не все
банки могут работать с CHF или RUB.
5. Некоторые банки могут осуществлять только покупку определённойgвалюты
или только продажу. Например, в таблице выше банк ввел запрет на продажу
CHF.
6. Сервис должен:
• Предоставлять возможность загрузить файл в систему (с помощью
RESTful API)
• Возвращать курс покупки указанной валюты всеми банками. Должна
быть возможность сортировки результатов по убыванию/возрастанию.
• Возвращать курс продажи указанной валюты всеми банками. Должна
быть возможность сортировки результатов по убыванию/возрастанию.
• Предоставлять возможность обновить курс покупки или продажи
указанной валюты для отдельно взятого банка. В том числе должна быть
возможность установить/снять запрет на покупку или продажу
определенной валюты.
• Удалить все предложения по покупке/продаже валюты для
определенного банка.
• Создавать отчет с наилучшими предложениями по покупке и продаже
всех валют, который в какой-то форме содержит следующую
информацию:
Код валюты Покупка Продажа
Курс Банк Курс Банк
USD 26.7 Банк 1 27.2 Банк 2
EUR 30.1 Банк 3 31.0 Банк 3
... ... ... ... ...

7. Сервис должен предоставлять RESTful API для доступа к своему функционалу.

8. Сервис должен быть реализован с использованием JDK 8 или более поздних
версий.
9. Использование других фрэймворков и библиотек на усмотрение кандидата.
10. Покрытие кода unit test-ами на усмотрение кандидата.
11. Если возникает вопрос, ответа на который нет в задании то кандидат может
самостоятельно сделать допущение, но при этом задокументировать и
прислать все допущения вместе с выполненным заданием.
12. Оптимальный срок выполнения задания - от 2-3 дня.
