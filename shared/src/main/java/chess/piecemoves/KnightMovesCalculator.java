package chess.piecemoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class KnightMovesCalculator extends PieceMovesCalculator {

    public KnightMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void iterateMods(ChessPosition myPosition, int[] i, int[] j) {
        myMoves = new HashSet<>();
        ChessMove myMove;
        ChessPosition newPosition;
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        for (int mod1 : i) {
            for (int mod2 : j) {
                newPosition = new ChessPosition(row + mod1, col + mod2);
                if (newPosition.inBounds() && (board.getPiece(newPosition) == null ||
                        board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor())) {
                    myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
                newPosition = new ChessPosition(row + mod2, col + mod1);
                if (newPosition.inBounds() && (board.getPiece(newPosition) == null ||
                        board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor())) {
                    myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
            }
        }
    }

    public void createMoves() {
        int[] i = {-2, 2};
        int[] j = {-1, 1};
        iterateMods(myPosition, i, j);
    }
}