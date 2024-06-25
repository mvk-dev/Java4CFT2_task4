package task4;

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
import task4.components.DbWriter;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.util.List;

@Testcontainers
@SpringBootTest(classes = {TestConfig.class},
        useMainMethod = SpringBootTest.UseMainMethod.NEVER, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class DbWriterTests {
    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");
    private static List<DataContainer<FileRecord>> dataList;

    @Autowired
    DbWriter<DataContainer<FileRecord>> writer;

    @BeforeAll
    static void setUpData() {
        dataList = TestUtils.createTestFileData();
    }

    @Test
    public void should_Success_When_WritingDataToDb() {
        Assertions.assertDoesNotThrow(() -> writer.write(dataList));
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        TestUtils.configureProperties(registry, postgres);
    }
}
