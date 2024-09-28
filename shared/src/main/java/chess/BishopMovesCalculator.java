package chess;

public class BishopMovesCalculator extends PieceMovesCalculator {

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        createMoves();
    }

    public void createMoves() {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition;
        int[] i = {-1, 1};
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