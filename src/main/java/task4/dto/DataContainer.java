package task4.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataContainer<E> {
    @Getter
    private final ContainerMetadata metadata;
    @Setter
    private List<E> entries;

    public DataContainer(ContainerMetadata metadata, List<E> entries) {
        this.metadata = metadata;
        this.entries = entries;
    }

    public List<E> getEntries() {
        return new ArrayList<>(entries);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DataContainer<?> that = (DataContainer<?>) object;
        return Objects.equals(metadata, that.metadata) && Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(metadata);
        result = 31 * result + Objects.hashCode(entries);
        return result;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "source=" + metadata +
                ", entries=" + entries +
                '}';
    }
}
