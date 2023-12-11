package com.ksm.domino.cli.command.job;

import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;
import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "get", header = "%n@|green Retrieves a single job by job id.|@")
public class JobGet extends AbstractDominoCommand {

    @ParentCommand
    private Job parent;

    private static final String NAME = "job get";

    @CommandLine.Option(names = {"--jobId"}, description = "Job ID to get", required = true)
    private String jobId;

    @Override
    public void execute() throws Exception {
        JobsApi api = new JobsApi(getApiClient(parent.domino));
        DominoJobsInterfaceJob job = api.getJob(jobId);
        output(job, parent.domino);
    }
}
