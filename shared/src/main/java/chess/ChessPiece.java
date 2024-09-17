package chess;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType myType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.myType = type;
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
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return myType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceMovesCalculator myMoves;
        switch (myType) {
            case BISHOP -> myMoves = new BishopMovesCalculator(board, myPosition);
            case KING -> myMoves = new KingMovesCalculator(board, myPosition);
            case KNIGHT -> myMoves = new KnightMovesCalculator(board, myPosition);
            case PAWN -> myMoves = new PawnMovesCalculator(board, myPosition);
//            case QUEEN -> myMoves = new QueenMovesCalculator(board, myPosition);
            case ROOK -> myMoves = new RookMovesCalculator(board, myPosition);
            default -> myMoves = new PieceMovesCalculator(board, myPosition);
        }

        return myMoves.pieceMoves();
    }
}

