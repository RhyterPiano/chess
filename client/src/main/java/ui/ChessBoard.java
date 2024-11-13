package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static chess.ChessGame.TeamColor.*;
import static ui.EscapeSequences.*;


public class ChessBoard {

    public ChessBoard() {

    }

    private void printBoard(ChessGame.TeamColor color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        drawHeader(out, color);
        drawBoard(out, color);
    }

    private void drawHeader(PrintStream out, ChessGame.TeamColor color) {
        //code to draw the first and last row from the team's desired color
    }

    private void drawBoard(PrintStream out, ChessGame.TeamColor color) {

    }
}

