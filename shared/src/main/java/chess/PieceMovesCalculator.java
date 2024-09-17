package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PieceMovesCalculator {
    public ChessBoard board;
    public ChessPosition myPosition;
    public Collection<ChessMove> myMoves = new HashSet<>();
    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> pieceMoves() {
        return myMoves;
    }

    public boolean inBounds(ChessPosition newPosition) {
        int row = newPosition.getRow();
        int col = newPosition.getColumn();
        if (row > 8 || row < 1) {
            return false;
        } else return col <= 8 && col >= 1;
    }
}

