package chess.piecemoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public abstract class PieceMovesCalculator {
    ChessBoard board;
    ChessPosition myPosition;
    HashSet<ChessMove> myMoves = new HashSet<>();

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
    }

    public abstract void createMoves();

    public Collection<ChessMove> getMoves() {
        return myMoves;
    }
}