package ui;

import chess.ChessGame;
import chess.ChessPiece;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static chess.ChessGame.TeamColor.*;
import static chess.EscapeSequences.*;


public class ChessBoard {

    public ChessBoard() {

    }

    //for testing
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        chess.ChessBoard game = new chess.ChessBoard();
        game.resetBoard();
        board.printBoard(BLACK, game);
    }

    void printBoard(ChessGame.TeamColor color, chess.ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        drawHeaders(out, color);
        drawBoard(out, color, board);
        drawHeaders(out, color);

        out.print(RESET_BG_COLOR);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private void drawHeaders(PrintStream out, ChessGame.TeamColor color) {
        setHeaders(out);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.print(" 0 ");
        setHeaders(out);
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
         out.print(SET_TEXT_COLOR_LIGHT_GREY);
         out.print(" 0 ");
         out.print(RESET_BG_COLOR);
         out.print("\n");
    }

    private void drawHeader(PrintStream out, char a) {
        out.print(" " + a  + "\u2003");
    }


    private void drawBoard(PrintStream out, ChessGame.TeamColor color, chess.ChessBoard board) {
        int[] rowNums = createRowOrder(color);
        for(int i : rowNums) {
            setHeaders(out);
            out.print(" " + i + " ");
            drawRow(out, i, board, color);
            setHeaders(out);
            out.print(" " + i + " ");
            out.print(RESET_BG_COLOR);
            out.print('\n');
        }
    }

    private void drawRow(PrintStream out, int i, chess.ChessBoard board, ChessGame.TeamColor color) {
        int[] colOrder = createRowOrder(color);
        ChessPiece[] row = board.getRow(i);
        int tileColor = i;
        if (color == BLACK) {
            tileColor++;
        }
        for(int col : colOrder) {
            tileColor = doCheckerBoard(out, tileColor);
            if (row[col-1] == null) {
                out.print(EMPTY);
            } else {
                setPieceColor(out, row[col-1]);
                out.print(row[col-1]);
            }
        }
    }

    private int[] createRowOrder(ChessGame.TeamColor color) {
        int[] rowNums;
        switch(color) {
            case WHITE -> {
                rowNums = new int[]{8, 7, 6, 5, 4, 3, 2, 1};
                break;
            }
            case BLACK -> {
                rowNums = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + color);
        }
        return rowNums;
    }

    private static void setHeaders(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private int doCheckerBoard(PrintStream out, int i) {
        i =  i % 2;
        switch(i) {
            case 0 -> {
                setLight(out);
                return 1;
            } case 1 -> {
                setDark(out);
                return 0;
            }
        }
        return -1;
    }

    private void setPieceColor(PrintStream out, ChessPiece piece) {
        switch(piece.getTeamColor()) {
            case WHITE -> out.print(SET_TEXT_COLOR_WHITE);
            case BLACK -> out.print(SET_TEXT_COLOR_BLACK);
        }
    }

    private static void setLight(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private static void setDark(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
    }
}

