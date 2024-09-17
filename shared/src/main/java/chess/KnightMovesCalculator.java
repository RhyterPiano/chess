package chess;

public class KnightMovesCalculator extends PieceMovesCalculator {

    public KnightMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int[] i = {-2, 2};
        int[] j = {-1, 1};
        for (int mod : i) {
            for (int mod2 : j) {
                ChessPosition newPosition = new ChessPosition(row + mod, col + mod2);
                ChessPosition newPosition2 = new ChessPosition(row + mod2, col + mod);
                if (inBounds(newPosition)) {
                    ChessPiece other = board.getPiece(newPosition);
                    if (other == null || other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                    }
                }
                if (inBounds(newPosition2)) {
                    ChessPiece other = board.getPiece(newPosition2);
                    if (other == null || other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        ChessMove myMove = new ChessMove(myPosition, newPosition2, null);
                        myMoves.add(myMove);
                    }
                }
            }
        }

    }

    public boolean inBounds(ChessPosition newPosition) {
        int row = newPosition.getRow();
        int col = newPosition.getColumn();
        if (row > 8 || row < 1) {
            return false;
        } else return col <= 8 && col >= 1;
    }
}