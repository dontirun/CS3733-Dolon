package UndoActionManager;

/**
 * Created by Walter on 4/16/2016.
 */
public interface IAction {
    public boolean doAction();
    public boolean undoAction();
    public boolean redoAction();
    public boolean isValid();
}
