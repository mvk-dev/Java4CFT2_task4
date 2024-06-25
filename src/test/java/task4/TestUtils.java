package task4;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import task4.components.FileReader;
import task4.dto.ContainerMetadata;
import task4.dto.ContainerType;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static final String PATH_TO_FOLDER = Path.of("").toAbsolutePath() + "\\_test\\";

    // Удаление тестовых файлов и каталога
    public static void deleteTestFiles() {
        File folder = new File(PATH_TO_FOLDER);

        File[] filesInFolder = folder.listFiles();
        if (filesInFolder != null) {
            for (File file : filesInFolder) {
                try {
                    boolean success = file.delete();
                } catch (Exception e) {
                    System.out.println();
                }
            }
        }
        folder.delete();
    }

    // Создание тестовых объектов
    public static List<DataContainer<FileRecord>> createTestFileData() {
        List<DataContainer<FileRecord>> dataList = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        FileRecord record1 = new FileRecord.Builder().login("login1").lastName("lastName1").name("name1")
                .patronymic("patronymic1").applicationType("web").accessDate(time.minusDays(1))
                .build();
        FileRecord record2 = new FileRecord.Builder().login("login2").lastName("lastName2").name("name2")
                .patronymic("patronymic2").applicationType("online")
                .build();

        FileRecord record3 = new FileRecord.Builder().login("login1").lastName("lastName1").name("name1")
                .patronymic("patronymic1").applicationType("online").accessDate(time.minusMinutes(127))
                .build();

        FileRecord record4 = new FileRecord.Builder().login("login3").lastName("lastName3").name("name3")
                .patronymic("patronymic3").applicationType("online").accessDate(time.minusMinutes(2343))
                .build();
        FileRecord record5 = new FileRecord.Builder().login("login2").lastName("lastName2").name("name2")
                .patronymic("patronymic2").applicationType("terminal").accessDate(time.minusMinutes(22))
                .build();

        ContainerMetadata metadata = new ContainerMetadata("file_1.txt", ContainerType.FILE);
        dataList.add(new DataContainer<>(metadata, List.of(record1, record2, record3)));

        metadata = new ContainerMetadata("file_2.txt", ContainerType.FILE);
        dataList.add(new DataContainer<>(metadata, List.of(record4, record5)));

        return dataList;
    }

    // Создание тестовых файлов
    public static void createTestFiles(List<DataContainer<FileRecord>> dataList) {
        Path dir = Path.of(PATH_TO_FOLDER);
        if (!Files.exists(dir)) {
            try {
                Files.createDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (DataContainer<FileRecord> container : dataList) {
            String filename = PATH_TO_FOLDER + container.getMetadata().name();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (FileRecord record : container.getEntries()) {
                    String str = record.getStringForFile(FileReader.DELIMITER);
                    writer.write(str + System.lineSeparator());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void configureProperties(DynamicPropertyRegistry registry, PostgreSQLContainer<?> postgres) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.sql.init.mode", () -> "always");
    }
}
