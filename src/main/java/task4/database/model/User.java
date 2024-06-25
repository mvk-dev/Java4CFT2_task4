package task4.database.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("users")
public class User {
    @Getter
    @Id
    private Long id;

    private final String username;
    private final String fio;

    @MappedCollection(idColumn = "user_id", keyColumn = "id")
    private List<Login> logins;

    public User(String username, String fio) {
        this.username = username;
        this.fio = fio;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        User user = (User) object;
        return username.equals(user.username) && fio.equals(user.fio);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + fio.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fio='" + fio + '\'' +
                ", logins=" + logins +
                '}';
    }
}
