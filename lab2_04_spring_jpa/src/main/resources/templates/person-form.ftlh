<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить лицо</title>
</head>
<body>
    <h2>Добавить лицо</h2>

    <form method="post" action="/persons">

        <div>
            <label>Имя: <input type="text" name="name" required></label>
        </div>

        <div>
            <label>Тип:
                <select name="type" id="typeSelect" onchange="toggleField()">
                    <option value="CLIENT">Client</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
            </label>
        </div>

        <div id="emailField" style="display: none">
            <label>Email: <input type="email" name="email"/></label>
        </div>

        <div id="addressField">
            <label>Адрес: <input type="text" name="address"/></label>
        </div>

        <div>
            <label>Телефон: <input type="text" name="phoneNumber" placeholder="+7 900 000-00-00"/></label>
        </div>

        <button type="submit">Сохранить</button>
    </form>

    <script>
        function toggleField() {
            const type = document.getElementById('typeSelect').value;
            document.getElementById('emailField').style.display = type === 'ADMIN' ? 'block' : 'none';
            document.getElementById('addressField').style.display = type === 'CLIENT' ? 'block' : 'none';
        }
    </script>

</body>
</html>