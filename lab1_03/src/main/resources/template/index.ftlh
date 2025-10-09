<html>


<head>

    <style>
        label {
            color: red;
        }

        .div_style {
            border: 1px solid green
        }
    </style>

</head>

<body>
    <h1>
        Крестики нолики
    </h1>

<table width="300">
    <#list table as r>
        <tr>
            <td><img src = "/lab1_03/static/img/${r.f}"></td>
            <td><img src = "/lab1_03/static/img/${r.s}"></td>
            <td><img src = "/lab1_03/static/img/${r.t}"></td>
        </tr>
    </#list>
</table>

    <form method="post" action="game">
        <div>
            <label>Номер строки:</label>
            <input type = 'text' name = 'row'>
        </div>

        <div>
            <label>Номер столбца</label>
            <input type= 'text' name = ''>
        </div>
    </form>
</body>


<body>
<h1>Тест Freemarker</h1>
<img src="/lab1_03/static/img/img.png"/>
<form method='post' action='/lab03/user'>
    <div>
        <label>Фамилия:</label>
        <input type='text' name='surname' value='${surname}'></input>
    </div>
    <div>
        <label>Имя:</label>
        <input type='text' name='name' value='${name}'></input>
    </div>
    <div>
        <label>Роль:</label>
        <select name='role'>
            <option value="1">Студент</option>
            <option value="2">Преподаватель</option>
            <option value="3">Вольнопосещающий</option>
        </select>
    </div>
    <div class="div_style">
        <label>Принимаю условия соглашения</label>
        <input type="checkbox" name="commit">
    </div>

    <fieldset>
        <div>
            <label>хорошее</label>
            <input type="radio" name="mood" value="good">
        </div>
        <div>
            <label>плохое</label>
            <input type="radio" name="mood" value="bad">
        </div>
        <div>
            <label>пойдет</label>
            <input type="radio" name="mood" value="ok">
        </div>
    </fieldset>

    <div>
        <input type="submit" value="Отправить">
    </div>

</form>
</body>
</html>