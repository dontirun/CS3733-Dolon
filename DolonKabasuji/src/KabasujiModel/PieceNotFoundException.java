package KabasujiModel;

/**
 * @author Stephen Lafortune, srlafortune@wpi.edu
 * Thrown when a piece is not found.
 */
public class PieceNotFoundException extends Exception {
    public PieceNotFoundException() { super(); }
    public PieceNotFoundException(String message) { super(message); }
    public PieceNotFoundException(String message, Throwable cause) { super(message, cause); }
    public PieceNotFoundException(Throwable cause) { super(cause); }
}
