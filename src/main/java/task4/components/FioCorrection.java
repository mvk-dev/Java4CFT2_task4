package task4.components;

import org.springframework.core.annotation.Order;
import task4.dto.DataContainer;
import task4.dto.FileRecord;
import task4.utils.LogTransformation;

import java.util.function.Consumer;

@LogTransformation(logFileName = "app_logfile.log")
@Order(3)
public class FioCorrection implements Consumer<DataContainer<FileRecord>> {
    @Override
    public void accept(DataContainer<FileRecord> dataContainer) {
        dataContainer.getEntries().forEach(e -> {
            e.setName(e.getName().substring(0, 1).toUpperCase() + e.getName().substring(1).toLowerCase());
            e.setLastName(e.getLastName().substring(0, 1).toUpperCase() + e.getLastName().substring(1).toLowerCase());
            e.setPatronymic(e.getPatronymic().substring(0, 1).toUpperCase() + e.getPatronymic().substring(1).toLowerCase());
        });
    }
}
