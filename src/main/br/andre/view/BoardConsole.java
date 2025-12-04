package main.br.andre.view;

import main.br.andre.exception.ExplosionException;
import main.br.andre.exception.QuitException;
import main.br.andre.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardConsole {

    private Board board;
    private Scanner input = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;

        execGame();
    }

    private void execGame() {
        try {
            boolean continueGame = true;

            while (continueGame) {
                gameCycle();

                System.out.println("Another game? (y/n)");
                String answer = input.nextLine();

                if ("n".equalsIgnoreCase(answer)) {
                    continueGame = false;
                } else {
                    board.reset();
                }
            }

        } catch (QuitException e) {
            System.out.println("Quit game");
        } finally {
            input.close();
        }
    }

    private void gameCycle() {
        try {
            while (!board.targetReached()) {
                System.out.println(board);

                String answer = getInputText("Type (x,y): ");

                Iterator<Integer> xy = Arrays.stream(answer.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                answer = getInputText("1 - Open || 2 - Mark/Unmark: ");

                if ("1".equalsIgnoreCase(answer)) {
                    board.open(xy.next(), xy.next());
                } else if ("2".equalsIgnoreCase(answer)) {
                    board.mark(xy.next(), xy.next());
                }
            }

            System.out.println("You win");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("You lose");
        }
    }

    private String getInputText(String text) {
        System.out.print(text);
        String answer = input.nextLine();

        if ("quit".equalsIgnoreCase(answer)) {
            throw new QuitException();
        }

        return answer;
    }
}
