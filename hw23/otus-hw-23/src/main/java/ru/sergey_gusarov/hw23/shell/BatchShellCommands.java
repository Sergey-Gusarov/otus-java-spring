package ru.sergey_gusarov.hw23.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;


@ShellComponent
public class BatchShellCommands {

    private JobLauncher jobLauncher;
    private Job migrateBooksJob;

    @Autowired
    public BatchShellCommands(JobLauncher jobLauncher, Job migrateBooksJob) {
        this.jobLauncher = jobLauncher;
        this.migrateBooksJob = migrateBooksJob;
    }

    @ShellMethod("run-migration")
    public void startMigration() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(migrateBooksJob, new JobParametersBuilder().toJobParameters());
    }
}
