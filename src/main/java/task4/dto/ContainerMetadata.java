package task4.dto;

public record ContainerMetadata(String name, ContainerType type) {
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ContainerMetadata metadata = (ContainerMetadata) object;
        return name.equals(metadata.name) && type == metadata.type;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}

