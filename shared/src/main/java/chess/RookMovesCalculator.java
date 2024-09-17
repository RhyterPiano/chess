package chess;

public class RookMovesCalculator extends PieceMovesCalculator {

    public RookMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        while (++row <= 8) {
            ChessPosition newPosition = new ChessPosition(row, col);
            ChessPiece other = board.getPiece(newPosition);
            if (other == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            } else break;
        }
        row = myPosition.getRow();
        while (--row >= 1) {
            ChessPosition newPosition = new ChessPosition(row, col);
            ChessPiece other = board.getPiece(newPosition);
            if (other == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            } else break;
        }
        row = myPosition.getRow();
        while (++col <= 8) {
            ChessPosition newPosition = new ChessPosition(row, col);
            ChessPiece other = board.getPiece(newPosition);
            if (other == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            } else break;
        }
        col = myPosition.getColumn();
        while (--col >= 1) {
            ChessPosition newPosition = new ChessPosition(row, col);
            ChessPiece other = board.getPiece(newPosition);
            if (other == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            }  else break;
        }

    }
}