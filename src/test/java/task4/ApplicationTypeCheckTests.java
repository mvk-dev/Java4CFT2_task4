package task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task4.components.ApplicationTypeCheck;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.util.List;

public class ApplicationTypeCheckTests {

    @Test
    public void should_RenameApplicationType_When_NotEqualWebMobile() {
        ApplicationTypeCheck operation = new ApplicationTypeCheck();

        String result = "Other: desktop";
        FileRecord fileRecord = new FileRecord.Builder()
                .applicationType("desktop")
                .build();
        DataContainer<FileRecord> container = new DataContainer<>(null, List.of(fileRecord));
        operation.accept(container);

        Assertions.assertEquals(result, fileRecord.getApplicationType());
    }

}
