package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static chess.ChessGame.TeamColor.*;
import static ui.EscapeSequences.*;


public class ChessBoard {

    public ChessBoard() {

    }

    //for testing
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.printBoard(WHITE);
    }

    private void printBoard(ChessGame.TeamColor color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        drawHeaders(out, color);
        drawBoard(out, color);

        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private void drawHeaders(PrintStream out, ChessGame.TeamColor color) {
        setHeaders(out);
        out.print(EMPTY);
        assert(color != null);
        char[] headers;
        switch(color) {
            case WHITE -> {
                headers = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
                break;
            }
            case BLACK -> {
                headers = new char[]{'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'};
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + color);
        }
         for(char a : headers) {
             drawHeader(out, a);
         }
    }

    private void drawHeader(PrintStream out, char a) {
        out.print(a + EMPTY);
    }


    private void drawBoard(PrintStream out, ChessGame.TeamColor color) {

    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setHeaders(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }
}

