package chess;

import java.util.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PieceMovesCalculator that)) return false;
        return Objects.equals(board, that.board) && Objects.equals(myPosition, that.myPosition) && Objects.equals(myMoves, that.myMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, myPosition, myMoves);
    }
}

