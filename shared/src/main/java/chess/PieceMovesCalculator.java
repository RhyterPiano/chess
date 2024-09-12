package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PieceMovesCalculator {
    public ChessBoard board;
    public ChessPosition myPosition;
    public Collection<ChessMove> myMoves = new ArrayList<>();
    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> pieceMoves() {
        return myMoves;
    }
}

