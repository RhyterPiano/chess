package chess;

public class KingMovesCalculator extends PieceMovesCalculator {
     public KingMovesCalculator(ChessBoard board, ChessPosition myPosition) {
         super(board, myPosition);
         createMoves();
     }

     public void createMoves() {
         int row = myPosition.getRow();
         int col = myPosition.getColumn();
         int[] i;
         if (row == 8) {
             i = new int[]{-1, 0};
         } else if (row == 1) {
             i = new int[]{0, 1};
         } else {
             i = new int[]{-1, 1, 0};
         }
         int[] j;
         if (col == 1) {
             j = new int[]{1, 0};
         } else if (col == 8) {
             j = new int[]{-1, 0};
         } else {
            j = new int[]{-1, 1, 0};
            }
         ChessPiece me = board.getPiece(myPosition);
         for (int mod : i) {
             for (int mod2 : j) {
                 ChessPosition newPosition = new ChessPosition(row + mod, col + mod2);
                 ChessPiece otherPiece = board.getPiece(newPosition);
                 if (otherPiece == null || otherPiece.getTeamColor() != me.getTeamColor()) {
                     ChessMove myMove = new ChessMove(myPosition, newPosition, null);
                     myMoves.add(myMove);
                 }
             }
         }

     }
}