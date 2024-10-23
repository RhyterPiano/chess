package chess.piecemoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class KingMovesCalculator extends PieceMovesCalculator {

    public KingMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int[] i = {-1, 0, 1};
        int[] j = {-1, 1};
        KnightMovesCalculator knight = new KnightMovesCalculator(board, myPosition);
        knight.iterateMods(myPosition, i, j);
        Collection<ChessMove> moves = knight.getMoves();
        myMoves.addAll(moves);
    }
}