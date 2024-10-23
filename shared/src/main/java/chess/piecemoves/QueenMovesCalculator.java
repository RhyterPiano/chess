package chess.piecemoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class QueenMovesCalculator extends PieceMovesCalculator {

    public QueenMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition;
        //rook moves
        RookMovesCalculator rook = new RookMovesCalculator(board, myPosition);
        rook.createMoves();
        Collection<ChessMove> rookMoves = rook.getMoves();
        //bishop moves
        BishopMovesCalculator bishop = new BishopMovesCalculator(board, myPosition);
        bishop.createMoves();
        Collection<ChessMove> bishopMoves = bishop.getMoves();
        for (ChessMove move : rookMoves) {
            myMoves.add(move);
        }
        for (ChessMove move : bishopMoves) {
            myMoves.add(move);
        }
    }
}