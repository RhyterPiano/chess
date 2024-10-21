package chess.piecemoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

public class KingMovesCalculator extends PieceMovesCalculator {

    public KingMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition;
        int[] i = {-1, 0, 1};
        int[] j = {-1, 1};
        ChessMove myMove;
        for (int mod1 : i) {
            for (int mod2 : j) {
                newPosition = new ChessPosition(row + mod1, col + mod2);
                if (newPosition.inBounds() && (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor())) {
                    myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
                newPosition = new ChessPosition(row + mod2, col + mod1);
                if (newPosition.inBounds() && (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor())) {
                    myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
            }
        }
    }
}