package task4.database.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String fio;

    @OneToMany(targetEntity = Login.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Login> logins;

    public User(String username, String fio) {
        this.username = username;
        this.fio = fio;
    }

    public User() {
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
