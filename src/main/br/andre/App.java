package main.br.andre;

import main.br.andre.model.Board;

public class App {
    public static void main(String[] args) {

        Board board = new Board(6,6,6);

        board.open(3,3);

        System.out.println(board);
    }
}
