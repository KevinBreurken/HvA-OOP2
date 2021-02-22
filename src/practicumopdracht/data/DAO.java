package practicumopdracht.data;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    void addOrUpdate(Object T);
    void remove(Object T);
    boolean save();
    boolean load();
    boolean isEdited();
    void checkIfFileExists(String fileName);
}
