package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderControllers.LevelBuilderController;
import BuilderModel.LevelModel;
import BuilderModel.Piece;
import BuilderModel.PieceGroup;
import BuilderModel.Square;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;

/**
 * Created by Walter on 5/4/2016.
 */
public class BoardToBullpenAction implements IAction{

    LevelModel level;
    Piece piece;
    LevelBuilderController lbc;
    int row;
    int column;
    GridPane boardView;
    GridPane bullpenView;

    public BoardToBullpenAction(LevelModel level, Piece piece, LevelBuilderController lbc, int row, int column, GridPane boardView, GridPane bullpenView) {
        this.level = level;
        this.piece = piece;
        this.lbc = lbc;
        this.row = row;
        this.column = column;
        this.boardView = boardView;
        this.bullpenView = bullpenView;
    }

    @Override
    public boolean doAction() {


        level.getBoard().removePiece(piece.getUniqueID());
        piece.flipPieceVert();
        // Determine the colour to set for the tile
        for(Square squareToRemove : piece.squares){
            GridSquare tilePaneToClear = (GridSquare)lbc.getNodeByRowColumnIndex(row +
                    (squareToRemove.getRelRow()*-1), column + squareToRemove.getRelCol(), boardView);
            tilePaneToClear.setOnDragDetected(null);
            tilePaneToClear.setOnDragDone(null);
            tilePaneToClear.setOnMouseClicked(null);
            lbc.makeTileChangeableAgain(tilePaneToClear);

            if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getCovered() > -1){
                tilePaneToClear.setStyle("-fx-background-color: #28a2db");
            }
            else if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists() == true){
                if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getHint() == true){
                    tilePaneToClear.setStyle("-fx-background-color: orange");
                }
                else{
                    tilePaneToClear.setStyle("-fx-background-color: white");
                }
            }
            else if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists() == false){
                tilePaneToClear.setStyle("-fx-background-color: black");
            }

        }

        // Add piece to bullpen
        level.getBullpen().addPiece(piece);

        // Add piece view to bullpen view
        final PieceGroup currentPiece = new PieceGroup(piece);
        bullpenView.add(currentPiece.getGroup(), lbc.getNumberOfBullpenPieces() % 2, lbc.getNumberOfBullpenPieces() / 2);
        bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
        bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
        bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
        int gridH = (level.getBullpen().getPieces().size() + 2 - 1) / 2;
        lbc.setGridH(gridH);

        lbc.setNumberOfBullpenPieces(lbc.getNumberOfBullpenPieces()+1);
        return true;
    }

    @Override
    public boolean undoAction() {
        return false;
    }

    @Override
    public boolean redoAction() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
