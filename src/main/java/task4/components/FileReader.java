package task4.components;

import org.springframework.stereotype.Component;
import task4.dto.ContainerMetadata;
import task4.dto.ContainerType;
import task4.dto.DataContainer;
import task4.dto.FileRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Component
public class FileReader implements Reader<DataContainer<FileRecord>, String> {
    public static final String DELIMITER = " ";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private String path;
    private final List<DataContainer<FileRecord>> data = new ArrayList<>();

    private void checkPath(String path) {
        if (path.isBlank())
            throw new IllegalArgumentException("Folder path can not be empty");
    }

    @Override
    public void setSource(String path) {
        checkPath(path);
        this.path = path;
    }

    @Override
    public List<DataContainer<FileRecord>> read() {
        File folder = new File(path);
        if (!folder.exists())
            throw new RuntimeException("Folder does not exist: " + path);

        if (!folder.isDirectory())
            throw new RuntimeException("Path does not contain a folder: " + path);

        File[] filesInFolder = folder.listFiles();
        if (filesInFolder == null || filesInFolder.length == 0)
            throw new RuntimeException("Folder contains no files or is not available");

        for (File file : filesInFolder) {
            try {
                List<FileRecord> list = parse(file);
                if (list != null && !list.isEmpty())
                    data.add(new DataContainer<>(new ContainerMetadata(file.getName(), ContainerType.FILE), list));
            } catch (IOException e) {
                System.out.println();
            }
        }

        return data;
    }

    private List<FileRecord> parse(File file) throws IOException {
        if (!file.canRead())
            return null;

        final List<FileRecord> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String text;

            while ((text = reader.readLine()) != null) {
                try {
                    FileRecord fileRecord = parseRecord(text);
                    result.add(fileRecord);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return result;
    }

    private FileRecord parseRecord(String record) {
        FileRecord.Builder builder = new FileRecord.Builder();
        String[] values = record.split(DELIMITER);

        // Предполагается, что в случае пустого значения одного из элементов на его месте будет пробел
        try {
            for (int i = 0; i < values.length; i++) {
                String value = values[i].trim();
                if (value.isEmpty())
                    continue;

                switch (i) {
                    case 0 -> builder.login(value);
                    case 1 -> builder.lastName(value);
                    case 2 -> builder.name(value);
                    case 3 -> builder.patronymic(value);
                    case 4 -> builder.accessDate(LocalDateTime.parse(value, TIME_FORMATTER));
                    case 5 -> builder.applicationType(value.toLowerCase());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing: " + record + System.lineSeparator() + e.getMessage());
        }

        return builder.build();
    }
}
