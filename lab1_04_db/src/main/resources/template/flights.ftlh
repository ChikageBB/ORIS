<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Онлайн табло</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: auto;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: center;
        }

        th {
            background: #eee;
        }
    </style>
</head>

<body>
    <h1 style = "text-align: center">Онлайн табло</h1>

    <form method="get" action="${request.contextPath}/flights" style = "text-align: center; margin-bottom: 20px;">

        <label>Аэропорт:
            <select name = "airport" required>
                <#list airports as airport>
                    <option value="airport">${airport}</option>
                </#list>
            </select>
        <label>Тип:
            <select name="type">
                <option value="departure">Вылеты</option>
                <option value="arrival">Прилёты</option>
            </select>
        </label>
        <button type="submit">Показать</button>
    </form>


    <table>
        <tr>
            <th>Рейс</th>
            <th>Вылет</th>
            <th>Прилёт</th>
            <th>Статус</th>
            <th>Откуда</th>
            <th>Куда</th>
        </tr>

        <#list flights as f>
            <tr>
                <td>${f.flightId}</td>
                <td>${f.scheduledDeparture?string("HH:mm dd.MM")}</td>
                <td>${f.scheduledArrival?string("HH:mm dd.MM")}</td>
                <td>${f.status}</td>
                <td>${f.departureAirport}</td>
                <td>${f.arrivalAirport}</td>
            </tr>
        </#list>

        <#if flights?size == 0>
            <tr><td colspan="6">Нет данных</td></tr>
        </#if>

    </table>
</body>
</html>