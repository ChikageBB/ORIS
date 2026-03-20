<html>
<head>
    <meta charset="UTF-8">
    <title>Все лица</title>
</head>

<body>
    <h2>Все лица</h2>

    <a href="/persons/add">+ Добавить лицо</a>

    <table border="1" cellpadding="8" cellspacing="0">

        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Тип</th>
            <th>Телефон</th>
            <th>Доп. инфо</th>
        </tr>

        <#list persons as person>
            <tr>
                <td>${person.id}</td>
                <td>${person.name!"-"}</td>
                <td>${person.class.simpleName}</td>
                <td>
                    <#if person.phone??>
                        ${person.phone.number}
                    <#else>
                        -
                    </#if>
                </td>

                <td>
                    <#if person.class.simpleName == "Admin">
                        ${person.email!"-"}
                    <#elseif person.class.simpleName == "Client">
                        ${person.address!"-"}
                    </#if>
                </td>
            </tr>
        </#list>

    </table>

</body>
</html>