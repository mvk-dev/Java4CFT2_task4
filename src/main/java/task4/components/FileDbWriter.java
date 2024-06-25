package task4.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task4.database.model.Login;
import task4.database.model.User;
import task4.database.repository.LoginRepository;
import task4.database.repository.UserRepository;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileDbWriter implements DbWriter<DataContainer<FileRecord>> {
    private LoginRepository loginRepository;

    private UserRepository userRepository;

    @Autowired
    public FileDbWriter(LoginRepository loginRepository, UserRepository userRepository) {
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void write(List<DataContainer<FileRecord>> dataContainerList) {
        Map<User, List<FileRecord>> userLogins = new HashMap<>();

        dataContainerList.forEach(dataSource ->
                dataSource.getEntries().forEach(entry -> {
                    User user = new User(entry.getLogin(),
                            entry.getLastName() + " " + entry.getName() + " " + entry.getPatronymic());
                    if (!userLogins.containsKey(user)) {
                        userLogins.put(user, new ArrayList<>());
                    }
                    userLogins.get(user).add(entry);
                })
        );

        // Сохраняем пользователя и список его авторизаций (логинов)
        userLogins.forEach((u, records) -> {
            User user = userRepository.save(u);
            records.forEach(record -> {
                        Login login = new Login(record.getAccessDate(), user.getId(), record.getApplicationType());
                        loginRepository.save(login);
                    }
            );
        });
    }
}
