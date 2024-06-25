package task4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import task4.components.FileReader;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class FileRecord {
    private final String login;
    @Setter
    private String name;
    @Setter
    private String lastName;
    @Setter
    private String patronymic;
    private final LocalDateTime accessDate;
    @Setter
    private String applicationType;

    private FileRecord(Builder builder) {
        this(builder.login, builder.name, builder.lastName, builder.patronymic, builder.accessDate, builder.applicationType);
    }

    public String getStringForFile(String delim) {
        return login + delim + lastName + delim + name + delim + patronymic + delim
                + (accessDate == null ? "" : accessDate.format(FileReader.TIME_FORMATTER))
                + delim + applicationType;
    }

    @Override
    public String toString() {
        return "AuthRecord{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", accessDate=" + accessDate +
                ", applicationType='" + applicationType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        FileRecord that = (FileRecord) object;
        return Objects.equals(login, that.login) && Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName) && Objects.equals(patronymic, that.patronymic) && Objects.equals(accessDate, that.accessDate) && Objects.equals(applicationType, that.applicationType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(login);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(patronymic);
        result = 31 * result + Objects.hashCode(accessDate);
        result = 31 * result + Objects.hashCode(applicationType);
        return result;
    }

    public static class Builder {
        private String login;
        private String name;
        private String lastName;
        private String patronymic;
        private LocalDateTime accessDate;
        private String applicationType;

        public Builder login(String value) {
            login = value;
            return this;
        }

        public Builder name(String value) {
            name = value;
            return this;
        }

        public Builder lastName(String value) {
            lastName = value;
            return this;
        }

        public Builder patronymic(String value) {
            patronymic = value;
            return this;
        }

        public Builder accessDate(LocalDateTime value) {
            accessDate = value;
            return this;
        }

        public Builder applicationType(String value) {
            applicationType = value;
            return this;
        }

        public FileRecord build() {
            return new FileRecord(this);
        }
    }
}
