package task4;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task4.components.AuthDateCheck;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.time.LocalDateTime;
import java.util.List;

public class AuthDateCheckTests {
    private static FileRecord emptyDateRecord;

    @BeforeAll
    public static void setUp() {
        emptyDateRecord = new FileRecord.Builder().login("login").name("name").build();
    }

    @Test
    public void should_RemoveRecord_When_AuthDateIsEmpty() {
        AuthDateCheck operation = new AuthDateCheck();

        FileRecord record1 = new FileRecord.Builder().login("login1").name("name1").accessDate(LocalDateTime.now()).build();
        FileRecord record2 = new FileRecord.Builder().login("login2").lastName("lastName2").accessDate(LocalDateTime.now()).build();
        System.out.println("record2 = " + record2);

        List<FileRecord> expectedList = List.of(record1, record2);
        DataContainer<FileRecord> container = new DataContainer<>(null, List.of(emptyDateRecord, record1, record2));

        operation.accept(container);
        Assertions.assertEquals(expectedList, container.getEntries());
    }

    @Test
    public void should_MarkRecords_When_AuthDateIsEmpty() {
        AuthDateCheck operation = new AuthDateCheck();
        DataContainer<FileRecord> container = new DataContainer<>(null, List.of(emptyDateRecord));
        operation.accept(container);
        Assertions.assertEquals(emptyDateRecord, operation.getInvalidData().get(0).getEntries().get(0));
    }
}
