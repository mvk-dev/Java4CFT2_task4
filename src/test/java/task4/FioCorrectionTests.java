package task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import task4.config.ApplicationConfig;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.util.List;
import java.util.function.Consumer;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = ApplicationConfig.class)
public class FioCorrectionTests {

    // Ради интереса через Autowired
    @Autowired
    @Qualifier("getFioCorrection")
    Consumer<DataContainer<FileRecord>> operation;

    @Test
    public void should_InitCapUSerFio() {
        String name = "Гассан";
        String lastName = "Абдуррахман";
        String patronymic = "Хоттабыч";
        FileRecord fileRecord = new FileRecord.Builder()
                .name(name.toUpperCase())
                .lastName(lastName.toLowerCase())
                .patronymic(patronymic.toLowerCase())
                .build();
        DataContainer<FileRecord> container = new DataContainer<>(null, List.of(fileRecord));
        operation.accept(container);

        Assertions.assertEquals(name, fileRecord.getName());
        Assertions.assertEquals(lastName, fileRecord.getLastName());
        Assertions.assertEquals(patronymic, fileRecord.getPatronymic());
    }
}
