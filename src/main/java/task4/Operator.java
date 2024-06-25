package task4;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task4.components.DbWriter;
import task4.components.Reader;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.util.List;
import java.util.function.Consumer;

@Service
public class Operator {
    private final Reader<DataContainer<FileRecord>, String> reader;
    private final DbWriter<DataContainer<FileRecord>> dbWriter;
    private String pathToFolder;

    @Setter
    private List<Consumer<DataContainer<FileRecord>>> operations;

    @Autowired
    public void setPathToFolder(String pathToFolder) {
        this.pathToFolder = pathToFolder;
    }

    @Autowired
    public Operator(Reader<DataContainer<FileRecord>, String> reader,
                    DbWriter<DataContainer<FileRecord>> dbWriter,
                    List<Consumer<DataContainer<FileRecord>>> operations) {
        this.reader = reader;
        this.dbWriter = dbWriter;
        this.operations = operations;
    }

    public void run() {
        reader.setSource(pathToFolder);
        List<DataContainer<FileRecord>> data = reader.read();

        data.forEach(container -> {
            for (Consumer<DataContainer<FileRecord>> operation : operations) {
                operation.accept(container);
            }
        });

        dbWriter.write(data);
    }
}
