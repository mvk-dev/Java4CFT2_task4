package task4;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import task4.components.FileReader;
import task4.components.Reader;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.util.List;

import static task4.TestUtils.PATH_TO_FOLDER;

@ContextConfiguration(classes = {FileReader.class})
@SpringJUnitConfig
public class FileReaderTests {
    @Autowired
    private Reader<DataContainer<FileRecord>, String> reader;
    private static List<DataContainer<FileRecord>> initDataList;

    // Создание тестовых файлов
    @BeforeAll
    public static void setUpEnv() {
        initDataList = TestUtils.createTestFileData();
        TestUtils.deleteTestFiles();
        TestUtils.createTestFiles(initDataList);
    }

    @Test
    void should_Success_When_ReadFilesInFolder() {
        reader.setSource(PATH_TO_FOLDER);
        List<DataContainer<FileRecord>> readData = reader.read();

        Assertions.assertEquals(initDataList.size(), readData.size());
        Assertions.assertEquals(initDataList, readData);
    }

    // Удаление тестовых файлов
    @AfterAll
    public static void clearEnv() {
        TestUtils.deleteTestFiles();
    }
}
