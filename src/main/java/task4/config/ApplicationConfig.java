package task4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import task4.components.ApplicationTypeCheck;
import task4.components.AuthDateCheck;
import task4.components.FioCorrection;
import task4.dto.DataContainer;
import task4.dto.FileRecord;
import task4.utils.LogUtils;

import java.util.Scanner;
import java.util.function.Consumer;

@Configuration
public class ApplicationConfig {

    @Bean
    Consumer<DataContainer<FileRecord>> getFioCorrection() {
        return LogUtils.get(new FioCorrection());
    }

    @Bean
    Consumer<DataContainer<FileRecord>> getApplicationTypeCheck() {
        return LogUtils.get(new ApplicationTypeCheck());
    }

    @Bean
    Consumer<DataContainer<FileRecord>> getAuthDateCheck() {
        return LogUtils.get(new AuthDateCheck());
    }

    @Bean
    @Lazy
    String getFolderPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите путь к файлу:");
        return scanner.nextLine();
    }

}
