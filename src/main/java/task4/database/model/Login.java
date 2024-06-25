package task4.database.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("logins")
public class Login {
    @Id
    private Long id;

    @Column("access_date")
    private final LocalDateTime accessDate;

    @Column("user_id")
    private final Long userId;

    @Column("application")
    private final String application;

    public Login(LocalDateTime accessDate, Long userId, String application) {
        this.accessDate = accessDate;
        this.userId = userId;
        this.application = application;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", accessDate=" + accessDate +
                ", userId=" + userId +
                ", application='" + application + '\'' +
                '}';
    }
}
