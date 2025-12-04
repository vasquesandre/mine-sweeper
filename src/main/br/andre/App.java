package main.br.andre;

import main.br.andre.model.Board;
import main.br.andre.view.BoardConsole;

public class App {
    public static void main(String[] args) {

        Board board = new Board(6,6,6);
        new BoardConsole(board);
    }
}
