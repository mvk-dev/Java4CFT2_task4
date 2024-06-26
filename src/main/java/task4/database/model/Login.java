package task4.database.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logins")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_date")
    private LocalDateTime accessDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "application")
    private String application;

    public Login(LocalDateTime accessDate, Long userId, String application) {
        this.accessDate = accessDate;
        this.userId = userId;
        this.application = application;
    }

    public Login() {
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
