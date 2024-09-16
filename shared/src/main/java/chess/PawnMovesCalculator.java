package chess;

public class PawnMovesCalculator extends PieceMovesCalculator {
    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int[] i = {1, -1};
        for (int mod : i) {
            ChessPosition newPosition = new ChessPosition(row++, col + mod);
            ChessPiece other = board.getPiece(newPosition);
            if (other == null || other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            }
        }
    }
}