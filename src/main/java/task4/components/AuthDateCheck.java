package task4.components;

import org.springframework.core.annotation.Order;
import task4.dto.DataContainer;
import task4.dto.FileRecord;
import task4.utils.LogTransformation;
import task4.utils.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@LogTransformation(logFileName = "app_logfile.log")
@Order(1)
public class AuthDateCheck implements Consumer<DataContainer<FileRecord>> {
    private final List<DataContainer<FileRecord>> invalidData = new ArrayList<>();
    private String logFileName;

    public AuthDateCheck() {
        if (AuthDateCheck.class.isAnnotationPresent(LogTransformation.class)) {
            logFileName = this.getClass().getAnnotation(LogTransformation.class).logFileName();
            if (logFileName.isEmpty())
                logFileName = "logfile.log";
        }
    }

    @Override
    public void accept(DataContainer<FileRecord> dataContainer) {
        List<FileRecord> invalidEntries = new ArrayList<>();
        List<FileRecord> entries = dataContainer.getEntries();

        Iterator<FileRecord> iterator = entries.iterator();
        while (iterator.hasNext()) {
            FileRecord entry = iterator.next();
            if (entry.getAccessDate() == null) {
                invalidEntries.add(entry);
                iterator.remove();
            }
        }

        if (!invalidEntries.isEmpty()) {
            invalidData.add(new DataContainer<>(dataContainer.getMetadata(), invalidEntries));
            dataContainer.setEntries(entries);
        }

        if (!invalidData.isEmpty())
            writeToLog();
    }

    private void writeToLog() {
        LogUtils.writeToLog(logFileName, "Records with empty Access Date: " + System.lineSeparator() + invalidData, false);
    }

    public List<DataContainer<FileRecord>> getInvalidData() {
        return new ArrayList<>(invalidData);
    }
}

