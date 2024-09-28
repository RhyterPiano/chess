package chess;

public class QueenMovesCalculator extends PieceMovesCalculator {

    public QueenMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition;
        //rook moves
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
        //bishop moves
        i = new int[] {-1, 1};
        int[] j = {-1, 1};
        for (int mod1 : i) {
            for (int mod2 : j) {
                newPosition = new ChessPosition(row + mod1, col + mod2);
                while (newPosition.inBounds()) {
                    ChessPiece other = board.getPiece(newPosition);
                    if (other == null) {
                        ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                        newPosition = new ChessPosition(newPosition.getRow() + mod1, newPosition.getColumn() + mod2);
                    } else if (other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                        break;
                    } else break;
                }
                newPosition = new ChessPosition(row + mod2, col + mod1);
                while (newPosition.inBounds()) {
                    ChessPiece other = board.getPiece(newPosition);
                    if (other == null) {
                        ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                        newPosition = new ChessPosition(newPosition.getRow() + mod2, newPosition.getColumn() + mod1);
                    } else if (other.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                        myMoves.add(myMove);
                        break;
                    } else break;
                }
            }
        }

    }
}