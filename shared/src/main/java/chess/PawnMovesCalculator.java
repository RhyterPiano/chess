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

        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            for (int mod : i) {
                ChessPosition newPosition = new ChessPosition(row + 1, col + mod);
                ChessPiece other = board.getPiece(newPosition);
                if (other != null && other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
            }
            ChessPosition newPosition = new ChessPosition(row + 1, col);
            if (board.getPiece(newPosition) == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            }
        } else {
            for (int mod : i) {
                ChessPosition newPosition = new ChessPosition(row - 1, col + mod);
                ChessPiece other = board.getPiece(newPosition);
                if (other != null && other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
            }
            ChessPosition newPosition = new ChessPosition(row - 1, col);
            if (board.getPiece(newPosition) == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);

            }
        }
    }
}