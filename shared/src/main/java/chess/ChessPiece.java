package chess;

import chess.piecemoves.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType myType;
    private final ChessGame.TeamColor myColor;
    private Collection<ChessMove> myMoves;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType myType) {
        this.myType = myType;
        this.myColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return myColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return myType;
    }

    public void promote(PieceType newType) {
        if (myType == PieceType.PAWN) {
            myType = newType;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return myType == that.myType && myColor == that.myColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myType, myColor);
    }

    @Override
    public String toString() {
        String myString = null;
        if (myColor == ChessGame.TeamColor.WHITE) {
            switch(myType) {
                case ROOK -> myString = "r";
                case KING -> myString = "k";
                case QUEEN -> myString = "q";
                case BISHOP -> myString = "b";
                case PAWN -> myString = "p";
                case KNIGHT -> myString = "n";
            }
        } else if (myColor == ChessGame.TeamColor.BLACK){
            switch(myType) {
                case ROOK -> myString = "R";
                case KING -> myString = "K";
                case QUEEN -> myString = "Q";
                case BISHOP -> myString = "B";
                case PAWN -> myString = "P";
                case KNIGHT -> myString = "N";
            }
        } else myString = " ";
        return myString;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        switch(myType) {
            case ROOK -> myMoves = new RookMovesCalculator(board, myPosition).getMoves();
            case BISHOP -> myMoves = new BishopMovesCalculator(board, myPosition).getMoves();
            case PAWN -> myMoves = new PawnMovesCalculator(board, myPosition).getMoves();
            case KNIGHT -> myMoves = new KnightMovesCalculator(board, myPosition).getMoves();
            case KING -> myMoves = new KingMovesCalculator(board, myPosition).getMoves();
            case QUEEN -> myMoves = new QueenMovesCalculator(board, myPosition).getMoves();
        }
        return myMoves;
    }
}
