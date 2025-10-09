package ru.itis.dis403.lab03;

import java.util.List;

public class GameEngine {

    private List<Row> table;

    public GameEngine(List<Row> table) {
        this.table = table;
    }


    /*

    x . .
    . . .
    . . .
      |
      v

     */

    public void makeBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        // перебор всех ходов
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getCell(i, j).equals("none.jpg")) {
                    setCell(i, j, "o.jpg");
                    int moveVal = minimax(false); // оценить этот ход
                    setCell(i, j, "none.jpg");

                    if (moveVal > bestVal) {
                        bestRow = i;
                        bestCol = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        if (bestRow != -1 && bestCol != -1) {
            setCell(bestRow, bestCol, "o.jpg");
        }
    }




    public int minimax(boolean isMax) {
        int score = evaluate();
        // если кто-то выиграл - вернуть оценку
        if (score == 10 || score == -10) return score;
        // если ничья
        if (checkWinner() != null && checkWinner().equals("draw")) return 0;

        // компьютер - максимизатор, пытается максимизировать свои ходы ради победы (10 очков)
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // если клетка свободна поставить символ
                    if (getCell(i, j).equals("none.jpg")) {
                        setCell(i, j, "o.jpg");
                        // оцениваем ответ противника
                        best = Math.max(best, minimax(false)); // наибольший результат
                        setCell(i, j, "none.jpg"); // откат хода

                    }
                }
            }
            return best;
        } else {
        // игрок - минимизатор, пытается минимизировать ходы компьютера
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (getCell(i, j).equals("none.jpg")) {
                        setCell(i, j, "x.jpg");
                        best = Math.min(best, minimax(true));
                        setCell(i, j, "none.jpg");
                    }
                }
            }
            return best;
        }
    }


    private void setCell(int row, int col, String value) {
        Row r = table.get(row);
        if (col == 0) r.setF(value);
        else if (col == 1) r.setS(value);
        else r.setT(value);
    }

    // метод оценки
    private int evaluate() {
        String winner = checkWinner();
        if (winner == null) return 0;
        if (winner.equals("o.jpg")) return 10; // победа компьютера
        if (winner.equals("x.png")) return -10; // победа игрока
        return 0;
    }

    public String checkWinner() {
        // горизонтали
        for (Row row : table) {
            if (!row.getF().equals("none.jpg") &&
                row.getF().equals(row.getS()) &&
                row.getS().equals(row.getT())) {
                return row.getF();
            }
        }

        // вертикали
        for (int i = 0; i < 3; i++) {
            String a = getCell(0, i);
            String b = getCell(1, i);
            String c = getCell(2, i) ;
            if (!a.equals("none.jpg") && a.equals(b) && a.equals(c)) {
                return a;
            }
        }

        // диагонали
        String center = getCell(1, 1);
        if (!center.equals("none.jpg")) {
            if (center.equals(getCell(0, 0)) && center.equals(getCell(2, 2))) return center;
            if (center.equals(getCell(0, 2)) && center.equals(getCell(2, 0))) return center;
        }

        boolean full = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getCell(i, j).equals("none.jpg")) {
                    full = false;
                }
            }
        }

        return full ? "draw" : null;
    }

    private String getCell(int row, int col) {
        Row r = table.get(row);
        if (col == 0) return r.getF();
        if (col == 1) return r.getS();
        return r.getT();
    }


}
