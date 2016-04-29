package BuilderModel;

/**
 * Thrown when a piece is not found
 *
 * Created by Arthur on 4/29/2016.
 */
public class PieceNotFoundException extends Exception {
    public PieceNotFoundException() { super(); }
    public PieceNotFoundException(String message) { super(message); }
    public PieceNotFoundException(String message, Throwable cause) { super(message, cause); }
    public PieceNotFoundException(Throwable cause) { super(cause); }
}
