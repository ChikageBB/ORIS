<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Крестики-нолики</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 40px; }
        h1 { color: #007700; }
        table { margin: 20px auto; border-collapse: collapse; }
        td { width: 100px; height: 100px; border: 2px solid #333; text-align: center; vertical-align: middle; background-color: #f9f9f9; }
        td img { width: 80px; height: 80px; }
        label { color: #333; font-weight: bold; }
        input[type="text"] { width: 40px; text-align: center; }
        input[type="submit"] { margin-top: 10px; padding: 5px 15px; background-color: #007700; color: white; border: none; border-radius: 4px; cursor: pointer; }
        input[type="submit"]:hover { background-color: #009900; }
    </style>
</head>

<body>
<h1>Крестики-нолики</h1>

<table>
    <#list table as r>
        <tr>
            <td><img src="/lab1_03/static/img/${r.f}" alt="f"></td>
            <td><img src="/lab1_03/static/img/${r.s}" alt="s"></td>
            <td><img src="/lab1_03/static/img/${r.t}" alt="t"></td>
        </tr>
    </#list>
</table>

<form method="post" action="/lab1_03/game">
    <input type="hidden" name="uuid" value="${uuid}">
    <div>
        <label>Номер строки:</label>
        <input type="text" name="row" required>
    </div>
    <div>
        <label>Номер столбца:</label>
        <input type="text" name="column" required>
    </div>
    <div>
        <input type="submit" value="Сделать ход">
    </div>
</form>

</body>
</html>
