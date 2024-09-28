package chess;

public class RookMovesCalculator extends PieceMovesCalculator {

    public RookMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition;
        int[] i = {1, -1};
        for (int mod : i) {
            newPosition = new ChessPosition(row + mod, col);
            while (newPosition.inBounds()) {
                if (board.getPiece(newPosition) == null) {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                    newPosition = new ChessPosition(newPosition.getRow() + mod, newPosition.getColumn());
                } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                    break;
                } else break;
            }
            newPosition = new ChessPosition(row, col + mod);
            while (newPosition.inBounds()) {
                if (board.getPiece(newPosition) == null) {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                    newPosition = new ChessPosition(newPosition.getRow(), newPosition.getColumn() + mod);
                } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                    break;
                } else break;
            }
        }
    }
}