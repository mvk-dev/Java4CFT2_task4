package task4.components;

import org.springframework.core.annotation.Order;
import task4.dto.DataContainer;
import task4.dto.FileRecord;
import task4.utils.LogTransformation;

import java.util.function.Consumer;

@LogTransformation(logFileName = "app_logfile.log")
@Order(2)
public class ApplicationTypeCheck implements Consumer<DataContainer<FileRecord>> {

    @Override
    public void accept(DataContainer<FileRecord> dataContainer) {
        for (FileRecord entry : dataContainer.getEntries()) {
            String appType = entry.getApplicationType();

            if (!appType.equals(ApplicationTypes.MOBILE.name().toLowerCase())
                    && !appType.equals(ApplicationTypes.WEB.name().toLowerCase())) {
                entry.setApplicationType("Other: " + appType);
            }
        }
    }
}

enum ApplicationTypes {
    WEB, MOBILE, OTHER
}