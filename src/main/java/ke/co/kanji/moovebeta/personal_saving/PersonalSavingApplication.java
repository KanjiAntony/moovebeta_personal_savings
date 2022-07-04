package ke.co.kanji.moovebeta.personal_saving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class PersonalSavingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalSavingApplication.class, args);
    }

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
