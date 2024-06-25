package task4;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import task4.database.model.Login;
import task4.database.model.User;
import task4.database.repository.LoginRepository;
import task4.database.repository.UserRepository;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.util.ArrayList;
import java.util.List;

@Testcontainers
@SpringBootTest(classes = {TestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class IntegrationTests {
    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @Autowired
    private Operator operator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    // Создание тестовых файлов
    @BeforeAll
    static void setUpData() {
        List<DataContainer<FileRecord>> initDataList = TestUtils.createTestFileData();
        TestUtils.deleteTestFiles();
        TestUtils.createTestFiles(initDataList);
    }

    @Test
    public void should_Success_When_LoadDataFromFilesToDb() {
        int expectedUsers = 3;
        int expectedLogins = 4; // всего 5 - 1 с пустым временем

        // Выполнение
        Assertions.assertDoesNotThrow(() -> operator.run());

        // Проверяем количество загруженных сущностей
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        Assertions.assertEquals(expectedUsers, users.size());

        List<Login> logins = new ArrayList<>();
        loginRepository.findAll().iterator().forEachRemaining(logins::add);
        Assertions.assertEquals(expectedLogins, logins.size());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        TestUtils.configureProperties(registry, postgres);
    }

    // Удаление тестовых файлов
    @AfterAll
    public static void clearEnv() {
        TestUtils.deleteTestFiles();
    }
}
