package chess;

import java.net.http.WebSocketHandshakeException;

public class PawnMovesCalculator extends PieceMovesCalculator {

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
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
                if (row == 7) {
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                    myMoves.add(myMove);
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.ROOK);
                    myMoves.add(myMove);
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                    myMoves.add(myMove);
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                    myMoves.add(myMove);
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
            int[] i = {-1, 1};
            for (int mod : i) {
                newPosition = new ChessPosition(row + 1, col + mod);
                if (row == 7) {
                    ChessPiece other = board.getPiece(newPosition);
                    if (other != null && other.getTeamColor() != myPiece.getTeamColor()) {
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                        myMoves.add(myMove);
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                        myMoves.add(myMove);
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                        myMoves.add(myMove);
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                        myMoves.add(myMove);
                    }
                } else {
                    if (newPosition.inBounds() && board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()) {
                        myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                    }
                }
            }
        } else {
            newPosition = new ChessPosition(row - 1, col);
            if (newPosition.inBounds() && board.getPiece(newPosition) == null) {
                if (row == 2) {
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                    myMoves.add(myMove);
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.ROOK);
                    myMoves.add(myMove);
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                    myMoves.add(myMove);
                    myMove = new ChessMove (myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                    myMoves.add(myMove);
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
            int[] i = {-1, 1};
            for (int mod : i) {
                newPosition = new ChessPosition(row - 1, col + mod);
                if (row == 2) {
                    ChessPiece other = board.getPiece(newPosition);
                    if (other != null && other.getTeamColor() != myPiece.getTeamColor()) {
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                        myMoves.add(myMove);
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                        myMoves.add(myMove);
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                        myMoves.add(myMove);
                        myMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                        myMoves.add(myMove);
                    }
                } else {
                    if (newPosition.inBounds() && board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()) {
                        myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                    }
                }
            }




        }
    }
}