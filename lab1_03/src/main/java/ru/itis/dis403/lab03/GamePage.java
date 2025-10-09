package ru.itis.dis403.lab03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/game")
public class GamePage extends HttpServlet {

    private final Map<String, GameState> gamers = new HashMap<>();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String uuid = UUID.randomUUID().toString();

        GameState gameState = new GameState();
        gamers.put(uuid, gameState);


        req.setAttribute("table", gameState.getTable());
        req.setAttribute("uuid", uuid);

        req.getRequestDispatcher("/game.ftlh").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String row = req.getParameter("row");
        String column = req.getParameter("column");
        String uuid = req.getParameter("uuid");


        GameState gameState = gamers.get(uuid);

        if (gameState == null) {
            resp.sendRedirect(req.getContextPath() + "/game.ftlh");
            return;
        }

        List<Row> table = gameState.getTable();

        int r, c;

        try {
            r = Integer.parseInt(row) - 1;
            c = Integer.parseInt(column) - 1;
        } catch (NumberFormatException e) {
            req.setAttribute("table", table);
            req.setAttribute("uuid", uuid);
            req.setAttribute("message", "Invalid move");
            req.getRequestDispatcher("/game.ftlh").forward(req, resp);
            return;
        }

        if (r < 0 || r > 2 || c < 0 || c > 2) {
            req.setAttribute("table", table);
            req.setAttribute("uuid", uuid);
            req.setAttribute("message", "Invalid coordinates");
            req.getRequestDispatcher("/game.ftlh").forward(req, resp);
            return;
        }

        Row trow = table.get(r);
        String current; // текущая клетка

        if (c == 0) current = trow.getF();
        else if (c == 1) current = trow.getS();
        else current = trow.getT();

        if (!"none.jpg".equals(current)) {
            req.setAttribute("table", table);
            req.setAttribute("uuid", uuid);
            req.setAttribute("message", "Cell occupied");
            req.getRequestDispatcher("/game.ftlh").forward(req, resp);
            return;
        }

        if (c == 0) trow.setF("x.jpg");
        else if (c == 1) trow.setS("x.jpg");
        else trow.setT("x.jpg");

        GameEngine engine = new GameEngine(table);

        // Проверка: выиграл ли игрок
        String winner = engine.checkWinner();
        if ("x.jpg".equals(winner)) {
            req.setAttribute("table", table);
            gamers.remove(uuid); // финализируем игру
            req.getRequestDispatcher("/win.ftlh").forward(req, resp);
            return;
        }

        // Ход компьютера
        engine.makeBestMove();

        // Проверка после хода компьютера
        winner = engine.checkWinner();
        if ("o.jpg".equals(winner)) {
            req.setAttribute("table", table);
            gamers.remove(uuid);
            req.getRequestDispatcher("/lose.ftlh").forward(req, resp);
            return;
        } else if ("draw".equals(winner)) {
            req.setAttribute("table", table);
            gamers.remove(uuid);
            req.getRequestDispatcher("/draw.ftlh").forward(req, resp);
            return;
        }

        // Игра продолжается — возвращаемся на страницу игры
        req.setAttribute("table", table);
        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher("/game.ftlh").forward(req, resp);


    }
}
