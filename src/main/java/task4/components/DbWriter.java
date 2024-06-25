package task4.components;

import java.util.List;

public interface DbWriter<T> {
    void write(List<T> data);
}
