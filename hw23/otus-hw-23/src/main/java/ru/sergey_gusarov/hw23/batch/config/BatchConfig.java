package ru.sergey_gusarov.hw23.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowJob;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sergey_gusarov.hw23.domain.books.Author;
import ru.sergey_gusarov.hw23.domain.books.Book;
import ru.sergey_gusarov.hw23.mongo.domain.books.BookComment;
import ru.sergey_gusarov.hw23.mongo.domain.books.Genre;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    private final Logger logger = LoggerFactory.getLogger("Batch");

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ru.sergey_gusarov.hw23.mongo.repository.book.BookMongoRepository bookMongoRepository;// bookDao;

    @Autowired
    private ru.sergey_gusarov.hw23.mongo.repository.author.AuthorMongoRepository authorMongoRepository;

    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository(ResourcelessTransactionManager transactionManager) throws Exception {
        MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
        mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
        return mapJobRepositoryFactoryBean.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }


    @Bean(destroyMethod = "")
    public JpaPagingItemReader<Book> bookItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("select b from Book b")
                .build();
    }

    @Bean(destroyMethod = "")
    public JpaPagingItemReader<Author> authorItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("select a from Author a")
                .build();
    }


    @Bean
    public ItemProcessor<Book, ru.sergey_gusarov.hw23.mongo.domain.books.Book> bookItemProcessor() {
        return item -> {
            ru.sergey_gusarov.hw23.mongo.domain.books.Book mongoBook = new ru.sergey_gusarov.hw23.mongo.domain.books.Book();
            mongoBook.setTitle(item.getTitle());
            List<ru.sergey_gusarov.hw23.mongo.domain.books.Author> authors = new ArrayList<>();
            item.getAuthors().forEach(a ->
                    {
                        List<ru.sergey_gusarov.hw23.mongo.domain.books.Author> authorList =
                                authorMongoRepository.findByName(a.getName());
                        if (authorList.size() > 0) {
                            authors.add(authorList.get(0));
                            //author.setId(authorList.get(0).getId());
                        } else {
                            ru.sergey_gusarov.hw23.mongo.domain.books.Author newAuthor = new ru.sergey_gusarov.hw23.mongo.domain.books.Author();
                            newAuthor.setName(a.getName());
                            newAuthor = authorMongoRepository.save(newAuthor);
                            authors.add(newAuthor);
                        }
                    }
            );
            mongoBook.setAuthors(authors);
            List<Genre> genres = (item.getGenres() == null) ?
                    (new ArrayList<>()) : (item.getGenres().stream().map(g ->
                    new Genre(g.getName())).collect(Collectors.toList()));
            mongoBook.setGenres(genres);

            List<BookComment> bookComments = (item.getBookComments() == null) ?
                    (new ArrayList<>()) : (item.getBookComments().stream().map(c ->
                    new BookComment(c.getText())).collect(Collectors.toList()));

            mongoBook.setBookComments(bookComments);

            return mongoBook;
        };
    }


    @Bean
    public ItemProcessor<Author, ru.sergey_gusarov.hw23.mongo.domain.books.Author> authorItemProcessor() {
        return item -> {
            List<ru.sergey_gusarov.hw23.mongo.domain.books.Author> authorList =
                    authorMongoRepository.findByName(item.getName());
            if (authorList.size() > 0) {
                return authorList.get(0);
            } else {
                ru.sergey_gusarov.hw23.mongo.domain.books.Author newAuthor = new ru.sergey_gusarov.hw23.mongo.domain.books.Author();
                newAuthor.setName(item.getName());
                return authorMongoRepository.save(newAuthor);
            }
        };
    }

    @Bean
    public RepositoryItemWriter<ru.sergey_gusarov.hw23.mongo.domain.books.Book> bookItemMongoWriter() {
        return new RepositoryItemWriterBuilder<ru.sergey_gusarov.hw23.mongo.domain.books.Book>()
                .repository(bookMongoRepository).methodName("save")
                .build();
    }

    @Bean
    public RepositoryItemWriter<ru.sergey_gusarov.hw23.mongo.domain.books.Author> authorItemMongoWriter() {
        return new RepositoryItemWriterBuilder<ru.sergey_gusarov.hw23.mongo.domain.books.Author>()
                .repository(authorMongoRepository).methodName("save")
                .build();
    }


    @Bean
    public Job migrateBooksJob(Step migrateBooks, Step migrateAuthors) {
        FlowJob job = (FlowJob) jobBuilderFactory.get("migrateBooksJob")
                .incrementer(new RunIdIncrementer())
                .flow(migrateAuthors)
                .next(migrateBooks)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало миграции");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Окончание миграции");
                    }
                })
                .build();
        job.setRestartable(true);
        return job;
    }

    @Bean
    public Step migrateBooks(JpaPagingItemReader<Book> bookItemReader,
                             ItemWriter<ru.sergey_gusarov.hw23.mongo.domain.books.Book> bookItemMongoWriter) {
        return stepBuilderFactory.get("migrateBooks")
                .<Book, ru.sergey_gusarov.hw23.mongo.domain.books.Book>chunk(2)
                .reader(bookItemReader)
                .processor(bookItemProcessor())
                .writer(bookItemMongoWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step migrateAuthors(JpaPagingItemReader<Author> authorItemReader,
                               ItemWriter<ru.sergey_gusarov.hw23.mongo.domain.books.Author> authorItemMongoWriter) {
        return stepBuilderFactory.get("migrateAuthors")
                .<Author, ru.sergey_gusarov.hw23.mongo.domain.books.Author>chunk(2)
                .reader(authorItemReader)
                .processor(authorItemProcessor())
                .writer(authorItemMongoWriter)
                .allowStartIfComplete(true)
                .build();
    }
}
