package task4.components;

import java.util.List;

public interface Reader<T,E> {
    void setSource(E source);
    List<T> read();
}
