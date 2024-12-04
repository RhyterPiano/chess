package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn = TeamColor.WHITE;
    private ChessBoard board = new ChessBoard();

    private boolean isOver = false;

    public ChessGame() {
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public ChessGame makeCopy() {
        ChessGame chessCopy = new ChessGame();
        chessCopy.setTeamTurn(teamTurn);
        chessCopy.setBoard(board.makeCopy());
        return chessCopy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessGame chessGame)) {
            return false;
        }
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }

    private boolean isValid(ChessMove move) {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        try {
            ChessGame gameCopy = this.makeCopy();
            gameCopy.setTeamTurn(piece.getTeamColor());
            gameCopy.makeMove(move);
            return true;
        } catch (InvalidMoveException ex) {
            return false;
        }
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> moves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new HashSet<>();
        for (ChessMove move : moves) {
            if (board.getPiece(move.getStartPosition()) != null && isValid(move)) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (board == null || board.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException();
        }
        if (board.getPiece(move.getStartPosition()).pieceMoves(board, move.getStartPosition()).contains(move)) {
            ChessGame gameCopy = this.makeCopy();
            ChessPiece piece = board.getPiece(move.getStartPosition());
            if (move.getPromotionPiece() != null) {
                piece.promote(move.getPromotionPiece());
            }
            board.addPiece(move.getEndPosition(), piece);
            board.removePiece(move.getStartPosition());
            if (isInCheck(piece.getTeamColor())) {
                this.board = gameCopy.getBoard();
                throw new InvalidMoveException();
            }
            if (piece.getTeamColor() != teamTurn) {
                throw new InvalidMoveException();
            }
        } else {
            throw new InvalidMoveException();
        }
        if (teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
    }

    private ChessPosition findKing(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    return position;
                }
            }
        }
        return null;
    }

    private boolean containsPosition(ChessPosition myPosition, TeamColor color) {
        if (board == null) {
            return false;
        }
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition newPosition = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(newPosition);
                if (willHitKing(piece, color, myPosition, newPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean willHitKing(ChessPiece piece, TeamColor color, ChessPosition myPosition, ChessPosition newPosition) {
        if (piece != null && piece.getTeamColor() != color) {
            // If error, change piece.pieceMoves to be validMoves(newPosition)
            Collection<ChessMove> moves = piece.pieceMoves(board, newPosition);
            for (ChessMove move : moves) {
                if (move.getEndPosition().equals(myPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor);
        return containsPosition(kingPosition, teamColor);
        // Possible error from using pieceMoves instead of validMoves.
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            Collection<Collection<ChessMove>> validMoves = new HashSet<>();
            checkCanMove(validMoves, teamColor);
            return validMoves.isEmpty();
        }
        return false;
    }

    public void checkCanMove(Collection<Collection<ChessMove>> validMoves, TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> myMoves = validMoves(position);
                    if (!myMoves.isEmpty()) {
                        validMoves.add(myMoves);
                    }
                }
            }
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;
        }
        Collection<Collection<ChessMove>> validMoves = new HashSet<>();
        checkCanMove(validMoves, teamColor);
        return validMoves.isEmpty();
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
