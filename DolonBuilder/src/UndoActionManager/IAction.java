package UndoActionManager;

/** Interface for all undoable actions
 * Created by Walter on 4/16/2016.
 */
public interface IAction {
    /** does action
     * @return true if successful
     */
    public boolean doAction();
    /** undoes action
     * @return true if successful
     */
    public boolean undoAction();
    /** redoes action
     * @return true if successful
     */
    public boolean redoAction();
    /** checks if action is valid
     * @return true if valid
     */
    public boolean isValid();
}
