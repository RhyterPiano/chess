package chess;

public class PawnMovesCalculator extends PieceMovesCalculator {
    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    private void promotePawn(ChessPosition newPosition) {
        myMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
        myMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
        myMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
        myMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
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
                    if (newPosition.getRow() == 8) {
                        promotePawn(newPosition);
                    } else {
                        ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                    }
                }
            }
            ChessPosition newPosition = new ChessPosition(row + 1, col);
            if (board.getPiece(newPosition) == null) {
                if (newPosition.getRow() == 8) {
                    promotePawn(newPosition);
                } else {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
                if (myPosition.getRow() == 2) {
                    ChessPosition otherPosition = new ChessPosition(row + 2, col);
                    if (board.getPiece(otherPosition) == null) {
                        ChessMove newMove = new ChessMove(myPosition, otherPosition, null);
                        myMoves.add(newMove);
                    }
                }
            }
        } else {
            for (int mod : i) {
                ChessPosition newPosition = new ChessPosition(row - 1, col + mod);
                ChessPiece other = board.getPiece(newPosition);
                if (other != null && other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    if (newPosition.getRow() == 1) {
                        promotePawn(newPosition);
                    } else {
                        ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                    }
                }
            }
            ChessPosition newPosition = new ChessPosition(row - 1, col);
            if (board.getPiece(newPosition) == null) {
                if (newPosition.getRow() == 1) {
                    promotePawn(newPosition);
                } else {
                    ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
                if (myPosition.getRow() == 7) {
                    ChessPosition otherPosition = new ChessPosition(row - 2, col);
                    if (board.getPiece(otherPosition) == null) {
                        ChessMove newMove = new ChessMove(myPosition, otherPosition, null);
                        myMoves.add(newMove);
                    }
                }
            }

        }
    }
}