package data;

/**
 * Created by Martin on 01.06.2017.
 */
public class RefreshData {
    private int redo = 0;
    private int id_for_zeiterfassung_redo = 0;
    private boolean by_project;



    public int getRedo() {
        return redo;
    }

    public void setRedo(int redo) {
        this.redo = redo;
    }

    public int getId_for_zeiterfassung_redo() {
        return id_for_zeiterfassung_redo;
    }

    public void setId_for_zeiterfassung_redo(int id_for_zeiterfassung_redo) {
        this.id_for_zeiterfassung_redo = id_for_zeiterfassung_redo;
    }

    public boolean isBy_project() {
        return by_project;
    }

    public void setBy_project(boolean by_project) {
        this.by_project = by_project;
    }

}
