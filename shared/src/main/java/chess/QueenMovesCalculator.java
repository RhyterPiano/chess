package chess;

public class QueenMovesCalculator extends PieceMovesCalculator {

    public QueenMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super (board, myPosition);
        createMoves();
    }

    public void createMoves() {
        getRookMoves();
        getBishopMoves();
    }

    private void getRookMoves() {
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

    public void getBishopMoves() {
        // Here I need to implement code that will add the correct set of moves to the myMoves variable
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        while (++row <= 8 && ++col <= 8) {
//            row++; col++;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (this.board.getPiece(newPosition) == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(newPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            } else {
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        while (++row <= 8 && --col >= 1) {
//            row++; col--;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (this.board.getPiece(newPosition) == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(newPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            } else {
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        while (--row >= 1 && --col >= 1) {
//            row--; col--;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (this.board.getPiece(newPosition) == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(newPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            } else {
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        while (--row >= 1 && ++col <= 8) {
//            row--; col++;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (this.board.getPiece(newPosition) == null) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
            } else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(newPosition).getTeamColor()) {
                ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                myMoves.add(myMove);
                break;
            } else {
                break;
            }
        }
    }
}