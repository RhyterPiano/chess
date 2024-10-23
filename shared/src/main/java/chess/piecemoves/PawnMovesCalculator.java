package chess.piecemoves;

import chess.*;

public class PawnMovesCalculator extends PieceMovesCalculator {

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void promotePawnMoves(ChessPosition myPosition, ChessPosition newPosition) {
        ChessMove myMove;
        myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.BISHOP);
        myMoves.add(myMove);
        myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.ROOK);
        myMoves.add(myMove);
        myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.QUEEN);
        myMoves.add(myMove);
        myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
        myMoves.add(myMove);
    }

    private void checkValid(ChessPiece myPiece, ChessPosition newPosition) {
        ChessMove myMove;
        if (newPosition.inBounds() && board.getPiece(newPosition) != null &&
                board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()) {
            myMove = new ChessMove(myPosition, newPosition, null);
            myMoves.add(myMove);
        }
    }

    public void pawnPromotion1(int row, int col, ChessPosition myPosition,
                              ChessPosition newPosition) {
        ChessMove myMove;
        if (row == 7) {
            promotePawnMoves(myPosition, newPosition);
        } else {
            myMove = new ChessMove (myPosition, newPosition, null);
            myMoves.add(myMove);
            if (row == 2) {
                newPosition = new ChessPosition(row + 2, col);
                if (board.getPiece(newPosition) == null) {
                    myMove = new ChessMove (myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
            }
        }
    }

    public void pawnPromotion2(int row, int col, ChessPosition myPosition,
                               ChessPosition newPosition) {
        ChessMove myMove;
        if (row == 2) {
            promotePawnMoves(myPosition, newPosition);
        } else {
            myMove = new ChessMove (myPosition, newPosition, null);
            myMoves.add(myMove);
            if (row == 7) {
                newPosition = new ChessPosition(row - 2, col);
                if (board.getPiece(newPosition) == null) {
                    myMove = new ChessMove (myPosition, newPosition, null);
                    myMoves.add(myMove);
                }
            }
        }
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition;
        ChessMove myMove;
        ChessPiece myPiece = board.getPiece(myPosition);
        if (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            newPosition = new ChessPosition(row + 1, col);
            if (newPosition.inBounds() && board.getPiece(newPosition) == null) {
                pawnPromotion1(row, col, myPosition, newPosition);
            }
            int[] i = {-1, 1};
            for (int mod : i) {
                newPosition = new ChessPosition(row + 1, col + mod);
                if (row == 7) {
                    ChessPiece other = board.getPiece(newPosition);
                    if (other != null && other.getTeamColor() != myPiece.getTeamColor()) {
                        promotePawnMoves(myPosition, newPosition);
                    }
                } else {
                    checkValid(myPiece, newPosition);
                }
            }
        } else {
            newPosition = new ChessPosition(row - 1, col);
            if (newPosition.inBounds() && board.getPiece(newPosition) == null) {
                pawnPromotion2(row, col, myPosition, newPosition);
            }
            int[] i = {-1, 1};
            for (int mod : i) {
                newPosition = new ChessPosition(row - 1, col + mod);
                if (row == 2) {
                    ChessPiece other = board.getPiece(newPosition);
                    if (other != null && other.getTeamColor() != myPiece.getTeamColor()) {
                        promotePawnMoves(myPosition, newPosition);
                    }
                } else {
                    checkValid(myPiece, newPosition);
                }
            }




        }
    }
}