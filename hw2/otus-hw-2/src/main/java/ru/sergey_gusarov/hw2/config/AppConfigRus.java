package ru.sergey_gusarov.hw2.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.sergey_gusarov.hw2.repository.PersonRepository;
import ru.sergey_gusarov.hw2.repository.PersonRepositorySimple;
import ru.sergey_gusarov.hw2.service.testing.TestingService;
import ru.sergey_gusarov.hw2.service.testing.TestingServiceImplFile;
import ru.sergey_gusarov.hw2.service.user.PersonService;
import ru.sergey_gusarov.hw2.service.user.PersonServiceImpl;

@ComponentScan(basePackages = {
        "ru.sergey_gusarov.hw2.repository",
//И всё равно не понимаю, зачем вынесли..
// вынес для хранения всех настроек в одном месте - настроечном файле
        "ru.sergey_gusarov.hw2.service",
        "ru.sergey_gusarov.hw2.util"}
)
@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfigRus {

    @Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurerInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        //Ну и на будущее - нужно строчными называть. От этого масса проблем вохникает на windows|linux машинах.
        //ок
        ms.setBasenames("/i18/exception-messages", "/i18/shell-message");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    TestingService testingService(MessageSource messageSource) {
        return new TestingServiceImplFile(messageSource);
    }

    @Bean
    PersonRepository personRepository(MessageSource messageSource) {
        return new PersonRepositorySimple(messageSource);
    }

    @Bean
    PersonService personService(PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }
}
