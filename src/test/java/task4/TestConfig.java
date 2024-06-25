package task4;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class TestConfig {
    @Bean("getFolderPath")
    @Profile("test")
    String getFolderPath() {
        return TestUtils.PATH_TO_FOLDER;
    }
}
